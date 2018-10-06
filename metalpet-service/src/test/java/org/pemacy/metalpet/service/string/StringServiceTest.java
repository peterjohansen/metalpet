package org.pemacy.metalpet.service.string;

import org.junit.jupiter.api.Test;
import org.pemacy.metalpet.model.string.StringModification;
import org.pemacy.metalpet.service.string.modification.StringModificationHandler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author Peter André Johansen
 */
public class StringServiceTest {

	@Test
	public void simpleModify() {
		class TestModification implements StringModification {
			public final String property = "baz";
		}
		class TestModificationHandler implements StringModificationHandler<TestModification> {
			@Override
			public String apply(String original, TestModification modification) {
				assertThat(original, is(equalTo("bar")));
				assertThat(modification.property, is(equalTo("baz")));
				return "foo";
			}
		}
		final var service = new StringService(type -> new TestModificationHandler());
		final var actual = service.modify("bar", new TestModification());
		assertThat(actual, is(equalTo("foo")));
	}

}
