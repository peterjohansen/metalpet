package org.pemacy.metalpet.service.string;

import org.pemacy.metalpet.model.string.StringModification;

import java.util.function.BiFunction;

/**
 * @author Peter André Johansen
 */
@FunctionalInterface
public interface StringModificationHandler<T extends StringModification> extends BiFunction<String, T, String> {

	@Override
	String apply(String original, T modification);

}
