package org.pemacy.metalpet.service.file;

import com.google.common.collect.ImmutableSet;
import org.pemacy.metalpet.model.file.target.FileTarget;
import org.pemacy.metalpet.service.file.target.FileTargetHandlerFunction;

import java.nio.file.Path;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Peter André Johansen
 */
public class FileService {

	private final FileTargetHandlerFunction fileTargetHandlerFunction;

	public FileService(FileTargetHandlerFunction fileTargetHandlerFunction) {
		this.fileTargetHandlerFunction = checkNotNull(fileTargetHandlerFunction);
	}

	@SuppressWarnings("unchecked")
	public ImmutableSet<Path> findFiles(Path rootDirectory, FileTarget fileTarget) {
		checkNotNull(rootDirectory);
		checkNotNull(fileTarget);
		final var handler = fileTargetHandlerFunction.apply(fileTarget.getClass());
		checkNotNull(handler, "no handler for file target: " + fileTarget.getClass().getName());
		return handler.apply(rootDirectory, fileTarget);
	}

}
