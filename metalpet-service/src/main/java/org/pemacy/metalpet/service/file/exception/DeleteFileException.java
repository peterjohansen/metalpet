package org.pemacy.metalpet.service.file.exception;

import org.pemacy.metalpet.model.file.DeleteFilesOperation;

import java.nio.file.Path;

import static com.google.common.base.Preconditions.checkNotNull;

public class DeleteFileException extends RuntimeException {

	private final Path path;
	private final DeleteFilesOperation deleteFilesOperation;

	public DeleteFileException(Path path, DeleteFilesOperation deleteFilesOperation, Throwable cause) {
		super("unable to delete file: " + checkNotNull(path), cause);
		this.path = path;
		this.deleteFilesOperation = checkNotNull(deleteFilesOperation);
	}

	public DeleteFilesOperation getDeleteFilesOperation() {
		return deleteFilesOperation;
	}

	public Path getPath() {
		return path;
	}

}
