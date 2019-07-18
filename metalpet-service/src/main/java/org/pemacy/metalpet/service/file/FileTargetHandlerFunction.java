package org.pemacy.metalpet.service.file;

import org.pemacy.metalpet.model.file.FileTarget;

import java.util.function.Function;

@FunctionalInterface
public interface FileTargetHandlerFunction
	extends Function<Class<? extends FileTarget>, FileTargetHandler> {

	@Override
	FileTargetHandler apply(Class<? extends FileTarget> type);

}
