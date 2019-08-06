package org.pemacy.metalpet.service.input.exception;

import org.pemacy.metalpet.model.input.InputType;

import static com.google.common.base.Preconditions.checkNotNull;

public class UnknownInputTypeException extends RuntimeException {

	private final InputType inputType;

	public UnknownInputTypeException(InputType inputType) {
		super("unknown input type: " + checkNotNull(inputType));
		this.inputType = inputType;
	}

	public InputType getInputType() {
		return inputType;
	}

}
