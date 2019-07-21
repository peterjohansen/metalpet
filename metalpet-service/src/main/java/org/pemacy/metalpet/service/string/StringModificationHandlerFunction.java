package org.pemacy.metalpet.service.string;

import org.pemacy.metalpet.model.string.StringModification;

import java.util.Optional;
import java.util.function.Function;

@FunctionalInterface
public interface StringModificationHandlerFunction
	extends Function<Class<? extends StringModification>, Optional<StringModificationHandler>> {

	@Override
	Optional<StringModificationHandler> apply(Class<? extends StringModification> type);

}
