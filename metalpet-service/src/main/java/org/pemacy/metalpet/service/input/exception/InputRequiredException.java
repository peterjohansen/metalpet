package org.pemacy.metalpet.service.input.exception;

import org.pemacy.metalpet.model.input.UserInput;

import java.util.Objects;

public class InputRequiredException extends RuntimeException {

	private final UserInput userInput;

	public InputRequiredException(UserInput userInput) {
		super("user input is required: " + Objects.requireNonNull(userInput));
		this.userInput = userInput;
	}

	public UserInput getUserInput() {
		return userInput;
	}

}
