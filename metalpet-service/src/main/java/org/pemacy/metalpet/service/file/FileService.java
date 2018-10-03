package org.pemacy.metalpet.service.file;

import com.google.common.collect.ImmutableSet;
import org.pemacy.metalpet.model.file.FileTarget;
import org.pemacy.metalpet.model.file.FileTargetIgnore;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Peter André Johansen
 */
public class FileService {

	public ImmutableSet<Path> findMatches(Path root, FileTarget fileTarget) {
		checkNotNull(root);
		checkNotNull(fileTarget);

		final Set<Path> matchingPaths = new HashSet<>();
		final var pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/" + fileTarget.getGlob());
		try {
			Files.walkFileTree(root, new SimpleFileVisitor<>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
					if (fileTarget.getIgnore() != FileTargetIgnore.DIRECTORIES) {
						Objects.requireNonNull(dir);
						Objects.requireNonNull(attrs);
						if (pathMatcher.matches(dir)) {
							matchingPaths.add(dir);
						}
					}
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
					if (fileTarget.getIgnore() != FileTargetIgnore.FILES) {
						Objects.requireNonNull(file);
						Objects.requireNonNull(attrs);
						if (pathMatcher.matches(file)) {
							matchingPaths.add(file);
						}
					}
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ImmutableSet.copyOf(matchingPaths);
	}

}
