package org.pemacy.metalpet.service.string.modification;

import org.junit.jupiter.api.Test;
import org.pemacy.metalpet.model.string.ImmutableReplaceStringModification;
import org.pemacy.metalpet.service.string.ReplaceStringModificationHandler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ReplaceStringModificationHandlerTest {

	@Test
	public void replace() {
		final var result = new ReplaceStringModificationHandler().apply(
			"Hello, world!",
			ImmutableReplaceStringModification.builder().target(", world").replacement("").build()
		);
		assertThat(result, is(equalTo("Hello!")));
	}

}
