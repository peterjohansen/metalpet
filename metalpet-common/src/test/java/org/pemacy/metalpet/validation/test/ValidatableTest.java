package org.pemacy.metalpet.validation.test;

import org.junit.jupiter.api.Test;
import org.pemacy.metalpet.validation.Validatable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Peter André Johansen
 */
public class ValidatableTest {

	/**
	 * @author Peter André Johansen
	 */
	private static class MyClass implements Validatable {

		@Min(0)
		private final int anInteger;

		@NotEmpty
		private final List<@NotBlank String> aList;

		public MyClass(int anInteger, List<String> aList) {
			this.anInteger = anInteger;
			this.aList = aList;
		}

	}

	@Test
	public void customExceptionThrown() {
		class CustomException extends RuntimeException {}
		assertThrows(CustomException.class, () ->
			new MyClass(-1, Arrays.asList("foo", null, "bar"))
				.validate(violation -> new CustomException())
		);
	}

	@Test
	public void customNullExceptionMapperFails() {
		assertThrows(NullPointerException.class,
			() -> new MyClass(1, Arrays.asList("foo", "bar")).validate(null));
	}

	@Test
	public void illegalObjectFailsAfterConstruction() {
		assertThrows(IllegalArgumentException.class,
			() -> new MyClass(-1, Arrays.asList("foo", null, "bar")).validateAfterConstruction());
	}

	@Test
	public void validObjectOkAfterConstruction() {
		new MyClass(1, Arrays.asList("foo", "bar")).validateAfterConstruction();
	}

}
