package org.pemacy.metalpet.service.file;

import org.pemacy.metalpet.model.file.DeleteFilesOperation;
import org.pemacy.metalpet.service.operation.OperationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class DeleteFilesOperationHandler implements OperationHandler<DeleteFilesOperation> {

	private final FileService fileService;

	@Autowired
	public DeleteFilesOperationHandler(FileService fileService) {
		this.fileService = Objects.requireNonNull(fileService);
	}

	@Override
	public void accept(Path rootDirectory, DeleteFilesOperation operation) {
		checkNotNull(rootDirectory);
		checkNotNull(operation);

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
