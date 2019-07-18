package org.pemacy.metalpet.service.file;

import org.pemacy.metalpet.model.file.FileTarget;

import java.nio.file.Path;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class FileService {

	private final FileTargetHandlerFunction fileTargetHandlerFunction;

	public FileService(FileTargetHandlerFunction fileTargetHandlerFunction) {
		this.fileTargetHandlerFunction = checkNotNull(fileTargetHandlerFunction);
	}

	@SuppressWarnings("unchecked")
	public Set<Path> findFiles(Path rootDirectory, FileTarget fileTarget) {
		checkNotNull(rootDirectory);
		checkNotNull(fileTarget);
		final var handler = fileTargetHandlerFunction.apply(fileTarget.getClass());
		checkNotNull(handler, "no handler for file target: " + fileTarget.getClass().getName());
		return handler.apply(rootDirectory, fileTarget);
	}

}
