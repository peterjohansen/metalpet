package org.pemacy.metalpet.service.string.test;

import org.junit.jupiter.api.Test;
import org.pemacy.metalpet.model.string.ReplaceStringModification;
import org.pemacy.metalpet.service.string.ReplaceStringModificationHandler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author Peter André Johansen
 */
public class ReplaceStringModificationHandlerTest {

	@Test
	public void replace() {
		final var modification = new ReplaceStringModificationHandler();
		final var result = modification.apply("Hello, world!", new ReplaceStringModification(", world", ""));
		assertThat(result, is(equalTo("Hello!")));
	}

}
