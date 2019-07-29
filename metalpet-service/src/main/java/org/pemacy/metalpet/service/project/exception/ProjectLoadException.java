package org.pemacy.metalpet.service.project.exception;

public class ProjectLoadException extends RuntimeException {

	public ProjectLoadException(Throwable cause) {
		super("failed when attempting to load project", cause);
	}

}
