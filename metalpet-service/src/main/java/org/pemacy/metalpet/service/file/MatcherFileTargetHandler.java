package org.pemacy.metalpet.service.file;

import org.pemacy.metalpet.model.file.FileTargetIgnore;
import org.pemacy.metalpet.model.file.MatcherFileTarget;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

public class MatcherFileTargetHandler implements FileTargetHandler<MatcherFileTarget> {

	@Override
	public Set<Path> apply(Path rootDirectory, MatcherFileTarget fileTarget) {
		final var matches = new HashSet<Path>();

		try {
			Files.walkFileTree(rootDirectory, new FileVisitor<>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
					addAllMatches(matches, file, fileTarget, Set.of(FileTargetIgnore.FILES));
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) {
					return FileVisitResult.TERMINATE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
					addAllMatches(matches, dir, fileTarget, Set.of(FileTargetIgnore.DIRECTORIES));
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			throw new RuntimeException(e); // TODO
		}

		return matches;
	}

	private void addAllMatches(Set<Path> matches, Path path, MatcherFileTarget fileTarget, Set<FileTargetIgnore> ignores) {
		if (!ignores.contains(fileTarget.getIgnore())) {
			final var pathMatcher = FileSystems.getDefault().getPathMatcher(fileTarget.getGlob());
			if (pathMatcher.matches(path)) {
				matches.add(path);
			}
		}
	}

}
