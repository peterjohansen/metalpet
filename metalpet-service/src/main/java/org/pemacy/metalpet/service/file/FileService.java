package org.pemacy.metalpet.service.file;

import org.pemacy.metalpet.model.file.FileTarget;
import org.pemacy.metalpet.service.file.exception.NoSuchFileTargetHandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class FileService {

	private final FileTargetHandlerFunction fileTargetHandlerFunction;

	@Autowired
	public FileService(FileTargetHandlerFunction fileTargetHandlerFunction) {
		this.fileTargetHandlerFunction = checkNotNull(fileTargetHandlerFunction);
	}

	@SuppressWarnings("unchecked")
	public Set<Path> findFiles(Path rootDirectory, FileTarget fileTarget) {
		checkNotNull(rootDirectory);
		checkNotNull(fileTarget);
		final var handler = fileTargetHandlerFunction.apply(fileTarget.getClass())
			.orElseThrow(() -> new NoSuchFileTargetHandlerException(fileTarget.getClass()));
		return handler.apply(rootDirectory, fileTarget);
	}

}
