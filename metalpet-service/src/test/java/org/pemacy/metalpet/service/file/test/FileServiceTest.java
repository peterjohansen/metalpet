package org.pemacy.metalpet.service.file.test;

import org.junit.jupiter.api.Test;
import org.pemacy.metalpet.model.file.FileTarget;
import org.pemacy.metalpet.model.file.FileTargetIgnore;
import org.pemacy.metalpet.service.file.FileService;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

/**
 * @author Peter André Johansen
 */
public class FileServiceTest {

	private static Path getTestDirectory() throws URISyntaxException {
		return Paths.get(FileServiceTest.class.getResource("/dummy-file-structure").toURI());
	}

	@Test
	public void ignoreDirectories() throws URISyntaxException {
		final var fileService = new FileService();
		final var matches = fileService.findMatches(
			getTestDirectory(),
			new FileTarget("foo*", FileTargetIgnore.DIRECTORIES)
		);
		assertThat(matches, containsInAnyOrder(
			getTestDirectory().resolve("foo.md"),
			getTestDirectory().resolve("bar/foo.txt"),
			getTestDirectory().resolve("baz/foo.txt")
		));
	}

	@Test
	public void ignoreFiles() throws URISyntaxException {
		final var fileService = new FileService();
		final var matches = fileService.findMatches(
			getTestDirectory(),
			new FileTarget("foo*", FileTargetIgnore.FILES)
		);
		assertThat(matches, containsInAnyOrder(
			getTestDirectory().resolve("foo/")
		));
	}

	@Test
	public void matchAllTextFiles() throws URISyntaxException {
		final var fileService = new FileService();
		final var matches = fileService.findMatches(
			getTestDirectory(),
			new FileTarget("*{.txt,.md}", FileTargetIgnore.NONE)
		);
		assertThat(matches, containsInAnyOrder(
			getTestDirectory().resolve("bar/foo.txt"),
			getTestDirectory().resolve("baz/foo.txt"),
			getTestDirectory().resolve("foo/bar.txt"),
			getTestDirectory().resolve("foo.md")
		));
	}

}
