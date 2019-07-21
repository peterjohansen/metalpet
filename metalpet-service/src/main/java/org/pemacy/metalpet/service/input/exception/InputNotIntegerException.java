package org.pemacy.metalpet.service.input.exception;

public class InputNotIntegerException extends RuntimeException {

	private final String inputValue;

	public InputNotIntegerException(String inputValue, Throwable cause) {
		super("input value is not an integer: " + inputValue, cause);
		this.inputValue = inputValue;
	}

	public String getInputValue() {
		return inputValue;
	}

}
