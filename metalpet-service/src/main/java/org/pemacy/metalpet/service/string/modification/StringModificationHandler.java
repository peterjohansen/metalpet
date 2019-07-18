package org.pemacy.metalpet.service.string.modification;

import org.pemacy.metalpet.model.string.StringModification;

import java.util.function.BiFunction;

@FunctionalInterface
public interface StringModificationHandler<T extends StringModification> extends BiFunction<String, T, String> {

	@Override
	String apply(String original, T modification);

}
