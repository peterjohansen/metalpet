package org.pemacy.metalpet.service.string.test;

import org.junit.jupiter.api.Test;
import org.pemacy.metalpet.model.string.StringModification;
import org.pemacy.metalpet.service.string.StringModificationHandler;
import org.pemacy.metalpet.service.string.StringService;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author Peter André Johansen
 */
public class StringServiceTest {

	@Test
	public void simpleProcess() {
		class TestModification implements StringModification {
			public String delete = ", world";
		}
		class TestModificationHandler implements StringModificationHandler<TestModification> {
			@Override
			public String apply(String original, TestModification modification) {
				return original.replace(modification.delete, "");
			}
		}
		final var service = new StringService(Map.of(TestModification.class, new TestModificationHandler()));
		final var actual = service.process("Hello, world!", new TestModification());
		assertThat(actual, is(equalTo("Hello!")));
	}

}
