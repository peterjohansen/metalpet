package org.pemacy.metalpet.service.operation.exception;

import org.pemacy.metalpet.model.operation.Operation;

import static com.google.common.base.Preconditions.checkNotNull;

public class NoSuchOperationHandlerException extends RuntimeException {

	private final Class<? extends Operation> type;

	public NoSuchOperationHandlerException(Class<? extends Operation> type) {
		super("no such operation handler: " + checkNotNull(type));
		this.type = type;
	}

	public Class<?> getType() {
		return type;
	}

}
