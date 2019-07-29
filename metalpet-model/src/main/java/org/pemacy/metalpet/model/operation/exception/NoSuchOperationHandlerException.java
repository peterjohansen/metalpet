package org.pemacy.metalpet.model.operation.exception;

import org.pemacy.metalpet.model.operation.Operation;

import java.util.Objects;

public class NoSuchOperationHandlerException extends RuntimeException {

	private final Class<? extends Operation> type;

	public NoSuchOperationHandlerException(Class<? extends Operation> type) {
		super("no such operation handler: " + Objects.requireNonNull(type));
		this.type = type;
	}

	public Class<?> getType() {
		return type;
	}

}
