package org.pemacy.metalpet.service.string.modification;

import org.pemacy.metalpet.model.string.StringModification;

import java.util.function.Function;

@FunctionalInterface
public interface StringModificationHandlerFunction
	extends Function<Class<? extends StringModification>, StringModificationHandler> {

	@Override
	StringModificationHandler apply(Class<? extends StringModification> type);

}
