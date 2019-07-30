package org.pemacy.metalpet.service.file;

import org.pemacy.metalpet.model.file.DeleteFilesOperation;
import org.pemacy.metalpet.service.operation.OperationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class DeleteFilesOperationHandler implements OperationHandler<DeleteFilesOperation> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteFilesOperationHandler.class);

	private final FileService fileService;

	@Autowired
	public DeleteFilesOperationHandler(FileService fileService) {
		this.fileService = Objects.requireNonNull(fileService);
	}

	@Override
	public void accept(Path rootDirectory, DeleteFilesOperation operation) {
		checkNotNull(rootDirectory);
		checkNotNull(operation);

		LOGGER.debug("Running with parameters: root directory = {}, operation = {}", rootDirectory, operation);

		operation.getTargets().forEach(fileTarget ->
			fileService.findFiles(rootDirectory, fileTarget).forEach(path -> {
				try {
					Files.delete(path);
				} catch (IOException e) {
					throw new RuntimeException(e); // TODO
				}
			})
		);
	}

}
