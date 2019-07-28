package org.pemacy.metalpet.service.file;

import org.pemacy.metalpet.model.file.DeleteFilesOperation;
import org.pemacy.metalpet.service.operation.OperationHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

public class DeleteFilesOperationHandler implements OperationHandler<DeleteFilesOperation> {

	private final FileService fileService;

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
