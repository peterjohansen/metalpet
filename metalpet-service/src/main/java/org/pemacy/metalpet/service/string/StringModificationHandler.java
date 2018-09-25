package org.pemacy.metalpet.service.string;

import java.util.function.BiFunction;

/**
 * @author Peter André Johansen
 */
@FunctionalInterface
public interface StringModificationHandler<T> extends BiFunction<String, T, String> {

	@Override
	String apply(String original, T modification);

}
