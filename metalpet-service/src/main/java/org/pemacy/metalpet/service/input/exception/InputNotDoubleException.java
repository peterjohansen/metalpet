package org.pemacy.metalpet.service.input.exception;

public class InputNotDoubleException extends RuntimeException {

	private final String inputValue;

	public InputNotDoubleException(String inputValue, Throwable cause) {
		super("input value is not a double: " + inputValue, cause);
		this.inputValue = inputValue;
	}

	public String getInputValue() {
		return inputValue;
	}

}
