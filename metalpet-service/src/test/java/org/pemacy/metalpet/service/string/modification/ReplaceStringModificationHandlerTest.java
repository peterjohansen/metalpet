package org.pemacy.metalpet.service.string.modification;

import org.junit.jupiter.api.Test;
import org.pemacy.metalpet.model.string.ReplaceStringModification;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author Peter André Johansen
 */
public class ReplaceStringModificationHandlerTest {

	@Test
	public void replace() {
		final var result = new ReplaceStringModificationHandler().apply(
			"Hello, world!",
			new ReplaceStringModification(", world", "")
		);
		assertThat(result, is(equalTo("Hello!")));
	}

}
