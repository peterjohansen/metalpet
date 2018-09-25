package org.pemacy.metalpet.validation;

import javax.validation.Validation;
import javax.validation.Validator;

/**
 * This class is just to not expose the validator instance in
 * the {@link Validatable} interface.
 *
 * @author Peter André Johansen
 */
public class ValidatorInstance {

	private static final Validator VALIDATOR_INSTANCE = Validation.buildDefaultValidatorFactory().getValidator();

	private ValidatorInstance() {
		throw new UnsupportedOperationException();
	}

	static Validator get() {
		return VALIDATOR_INSTANCE;
	}

}
