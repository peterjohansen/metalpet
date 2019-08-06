package org.pemacy.metalpet.service.input.exception;

import org.pemacy.metalpet.model.input.UserInput;

import static com.google.common.base.Preconditions.checkNotNull;

public class InputRequiredException extends RuntimeException {

	private final UserInput userInput;

	public InputRequiredException(UserInput userInput) {
		super("user input is required: " + checkNotNull(userInput));
		this.userInput = userInput;
	}

	public UserInput getUserInput() {
		return userInput;
	}

}
