package org.pemacy.metalpet.service.input.exception;

import org.pemacy.metalpet.model.input.InputType;

import java.util.Objects;

public class UnknownInputTypeException extends RuntimeException {

	private final InputType inputType;

	public UnknownInputTypeException(InputType inputType) {
		super("unknown input type: " + Objects.requireNonNull(inputType));
		this.inputType = inputType;
	}

	public InputType getInputType() {
		return inputType;
	}

}
