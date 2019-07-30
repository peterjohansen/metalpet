package org.pemacy.metalpet.service.file.exception;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

public class WalkFileTreeException extends RuntimeException {

	private static String buildMessage(Path rootDirectory, Path specificPath) {
		final var message = new StringBuilder();
		message.append("a problem occurred while walking file tree ");
		Optional.ofNullable(specificPath).ifPresentOrElse(
			path -> message.append("at this path: ").append(path),
			() -> message.append("with this root directory:").append(rootDirectory)
		);
		return message.toString();
	}

	private final Path rootDirectory;
	private final Path specificPath;

	public WalkFileTreeException(Path rootDirectory, Path specificPath, Throwable cause) {
		super(buildMessage(Objects.requireNonNull(rootDirectory), specificPath), cause);
		this.rootDirectory = rootDirectory;
		this.specificPath = specificPath;
	}

	public Path getRootDirectory() {
		return rootDirectory;
	}

	public Optional<Path> getSpecificPath() {
		return Optional.ofNullable(specificPath);
	}

}
