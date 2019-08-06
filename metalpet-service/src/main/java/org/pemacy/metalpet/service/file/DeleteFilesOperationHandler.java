package org.pemacy.metalpet.service.file;

import org.pemacy.metalpet.model.file.DeleteFilesOperation;
import org.pemacy.metalpet.service.file.exception.DeleteFileException;
import org.pemacy.metalpet.service.operation.OperationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class DeleteFilesOperationHandler implements OperationHandler<DeleteFilesOperation> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteFilesOperationHandler.class);

	private final FileService fileService;

	@Autowired
	public DeleteFilesOperationHandler(FileService fileService) {
		this.fileService = checkNotNull(fileService);
	}

	@Override
	public void accept(Path rootDirectory, DeleteFilesOperation operation) {
		checkNotNull(rootDirectory);
		checkNotNull(operation);

		LOGGER.debug("Running with parameters: root directory = {}, operation = {}", rootDirectory, operation);

		operation.getTargets().forEach(fileTarget ->
			fileService.findFiles(rootDirectory, fileTarget).forEach(path -> {
				if (Files.isDirectory(path)) {
					deleteDirectory(path, operation);
				} else {
					deleteFile(path, operation);
				}
			})
		);
	}

	private void deleteDirectory(Path dir, DeleteFilesOperation operation) {
		try {
			Files.walk(dir).sorted(Comparator.reverseOrder()).forEach(path -> deleteFile(path, operation));
		} catch (IOException e) {
			throw new RuntimeException(e); // TODO
		}
	}

	private void deleteFile(Path file, DeleteFilesOperation operation) {
		try {
			Files.delete(file);
		} catch (IOException e) {
			throw new DeleteFileException(file, operation, e);
		}
	}

}
