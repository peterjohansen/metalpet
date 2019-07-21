package org.pemacy.metalpet.service.file;

import org.pemacy.metalpet.model.file.FileTarget;

import java.util.Optional;
import java.util.function.Function;

@FunctionalInterface
public interface FileTargetHandlerFunction
	extends Function<Class<? extends FileTarget>, Optional<FileTargetHandler>> {

	@Override
	Optional<FileTargetHandler> apply(Class<? extends FileTarget> type);

}
