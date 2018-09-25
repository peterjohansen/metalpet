package org.pemacy.metalpet.validation;

import javax.validation.ConstraintViolation;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Peter André Johansen
 */
public interface Validatable {

	/**
	 * Validates this object using Java bean validation and throws
	 * an exception provided by the given function if any
	 * violations are found. The function's first argument is the
	 * first {@link ConstraintViolation} that arose during
	 * validation.
	 */
	default void validate(Function<ConstraintViolation<Validatable>, ? extends RuntimeException> exceptionFunction) {
		checkNotNull(exceptionFunction, "exception provider cannot be null");
		final var violation = ValidatorInstance.get()
			.validate(this)
			.stream()
			.findFirst();
		if (violation.isPresent()) {
			throw exceptionFunction.apply(violation.get());
		}
	}

	/**
	 * Validates this object using Java bean validation and throws
	 * an {@link IllegalArgumentException} if any violations are
	 * found. This method is meant to replace the typical argument
	 * checking in a constructor so that in most cases
	 * preconditions don't have to be checked twice.
	 * <p>
	 * An example of usage:
	 * <pre>
	 *     public class MyEntity implements Validatable {
	 *         @Min(0)
	 *         private final int anInteger;
	 *
	 *         @NotEmpty
	 *         private final List<@NotBlank String> aList;
	 *
	 *         public MyEntity(String anInteger, List<String> aList) {
	 *             this.anInteger = anInteger;
	 *             this.aList = (aList != null ? ImmutableList.copyOf(aList) : null);
	 *             validateAfterConstruction();
	 *         }
	 *     }
	 * </pre>
	 */
	default void validateAfterConstruction() {
		validate(violation -> new IllegalArgumentException(violation.getMessage()));
	}

}
