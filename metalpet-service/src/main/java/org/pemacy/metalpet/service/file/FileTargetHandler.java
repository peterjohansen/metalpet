package org.pemacy.metalpet.service.file;

import org.pemacy.metalpet.model.file.FileTarget;

import java.nio.file.Path;
import java.util.Set;
import java.util.function.BiFunction;

@FunctionalInterface
public interface FileTargetHandler<T extends FileTarget> extends BiFunction<Path, T, Set<Path>> {

	@Override
	Set<Path> apply(Path rootDirectory, T fileTarget);

}
