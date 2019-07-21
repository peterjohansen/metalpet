package org.pemacy.metalpet.service.input;

import org.pemacy.metalpet.model.input.InputType;
import org.pemacy.metalpet.model.input.StandardInputType;
import org.pemacy.metalpet.model.input.UserInput;
import org.pemacy.metalpet.service.input.exception.*;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;

public class InputService {

	public boolean inputValueToBoolean(String inputValue) {
		if (inputValue.equalsIgnoreCase("y")) {
			return true;
		} else if (inputValue.equalsIgnoreCase("n")) {
			return false;
		}
		throw new InputNotBooleanException(inputValue);
	}

	public double inputValueToDouble(String inputValue) {
		try {
			return Double.parseDouble(inputValue);
		} catch (NumberFormatException e) {
			throw new InputNotDoubleException(inputValue, e);
		}
	}

	public int inputValueToInteger(String inputValue) {
		try {
			return Integer.parseInt(inputValue);
		} catch (NumberFormatException e) {
			throw new InputNotIntegerException(inputValue, e);
		}
	}

	public String nextUserInput() {
		return new Scanner(System.in, StandardCharsets.UTF_8).nextLine();
	}

	public Object parseInputvalue(InputType inputType, String inputValue) {
		checkNotNull(inputType);

		// TODO Extract this to allow custom input types
		final Map<InputType, Function<String, Object>> typeMap = Map.of(
			StandardInputType.STRING, value -> value,
			StandardInputType.BOOLEAN, this::inputValueToBoolean,
			StandardInputType.INTEGER, this::inputValueToInteger,
			StandardInputType.DOUBLE, this::inputValueToDouble
		);

		return Optional.ofNullable(typeMap.get(inputType))
			.map(function -> function.apply(inputValue))
			.orElseThrow(() -> new UnknownInputTypeException(inputType));
	}

	public String valueOrDefault(UserInput userInput, String inputValue) {
		checkNotNull(userInput);
		if (userInput.getDefaultValue().isEmpty() && inputValue == null) {
			throw new InputRequiredException(userInput);
		}
		return inputValue == null ? userInput.getDefaultValue().get() : inputValue;
	}

}
