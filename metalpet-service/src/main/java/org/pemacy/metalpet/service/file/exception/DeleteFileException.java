package org.pemacy.metalpet.service.file.exception;

import org.pemacy.metalpet.model.file.DeleteFilesOperation;

import java.nio.file.Path;
import java.util.Objects;

public class DeleteFileException extends RuntimeException {

	private final Path path;
	private final DeleteFilesOperation deleteFilesOperation;

	public DeleteFileException(Path path, DeleteFilesOperation deleteFilesOperation, Throwable cause) {
		super("unable to delete file: " + Objects.requireNonNull(path), cause);
		this.path = path;
		this.deleteFilesOperation = Objects.requireNonNull(deleteFilesOperation);
	}

	public DeleteFilesOperation getDeleteFilesOperation() {
		return deleteFilesOperation;
	}

	public Path getPath() {
		return path;
	}

}
