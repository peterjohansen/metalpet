package org.pemacy.metalpet.service.input.exception;

public class InputNotBooleanException extends RuntimeException {

	private final String inputValue;

	public InputNotBooleanException(String inputValue) {
		super("input value is not a boolean: " + inputValue);
		this.inputValue = inputValue;
	}

	public String getInputValue() {
		return inputValue;
	}

}
