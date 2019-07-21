package org.pemacy.metalpet.service.filetarget;

import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Test;
import org.pemacy.metalpet.model.file.FileTarget;
import org.pemacy.metalpet.service.file.FileTargetHandler;
import org.pemacy.metalpet.service.file.FileTargetService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class FileTargetServiceTest {

	@Test
	public void simpleFindFiles() {
		class TestFileTarget implements FileTarget {
			final String property = "baz";
		}
		class TestTargetHandler implements FileTargetHandler<TestFileTarget> {
			@Override
			public Set<Path> apply(Path rootDirectory, TestFileTarget fileTarget) {
				assertThat(rootDirectory, is(equalTo(Paths.get("bar"))));
				assertThat(fileTarget.property, is(equalTo("baz")));
				return ImmutableSet.of(Paths.get("foobar"));
			}
		}
		final var service = new FileTargetService(type -> Optional.of(new TestTargetHandler()));
		final var actual = service.findFiles(Paths.get("bar"), new TestFileTarget());
		assertThat(actual, is(equalTo(ImmutableSet.of(Paths.get("foobar")))));
	}

}
