package org.pemacy.metalpet.service.file;

import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Test;
import org.pemacy.metalpet.model.file.target.FileTarget;
import org.pemacy.metalpet.service.file.target.FileTargetHandler;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author Peter André Johansen
 */
public class FileServiceTest {

	@Test
	public void simpleFindFiles() {
		class TestFileTarget implements FileTarget {
			public final String property = "baz";
		}
		class TestTargetHandler implements FileTargetHandler<TestFileTarget> {
			@Override
			public ImmutableSet<Path> apply(Path rootDirectory, TestFileTarget fileTarget) {
				assertThat(rootDirectory, is(equalTo(Paths.get("bar"))));
				assertThat(fileTarget.property, is(equalTo("baz")));
				return ImmutableSet.of(Paths.get("foobar"));
			}
		}
		final var service = new FileService(type -> new TestTargetHandler());
		final var actual = service.findFiles(Paths.get("bar"), new TestFileTarget());
		assertThat(actual, is(equalTo(ImmutableSet.of(Paths.get("foobar")))));
	}
}
