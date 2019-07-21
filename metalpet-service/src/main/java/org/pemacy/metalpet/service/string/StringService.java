package org.pemacy.metalpet.service.string;

import org.pemacy.metalpet.model.string.StringModification;
import org.pemacy.metalpet.service.string.modification.StringModificationHandlerFunction;

import static com.google.common.base.Preconditions.checkNotNull;

public class StringService {

	private final StringModificationHandlerFunction stringModificationHandlerFunction;

	public StringService(StringModificationHandlerFunction stringModificationHandlerFunction) {
		this.stringModificationHandlerFunction = checkNotNull(stringModificationHandlerFunction);
	}

	@SuppressWarnings("unchecked")
	public String modify(String original, StringModification modification) {
		checkNotNull(original);
		checkNotNull(modification);
		final var handler = stringModificationHandlerFunction.apply(modification.getClass())
			.orElseThrow(() -> new IllegalArgumentException("no handler for string modification: " + modification.getClass().getName()));
		return handler.apply(original, modification);
	}

}
