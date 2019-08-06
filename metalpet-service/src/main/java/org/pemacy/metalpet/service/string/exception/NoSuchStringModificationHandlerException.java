package org.pemacy.metalpet.service.string.exception;

import org.pemacy.metalpet.model.string.StringModification;

import static com.google.common.base.Preconditions.checkNotNull;

public class NoSuchStringModificationHandlerException extends RuntimeException {

	private final Class<? extends StringModification> type;

	public NoSuchStringModificationHandlerException(Class<? extends StringModification> type) {
		super("no such string modification handler: " + checkNotNull(type));
		this.type = type;
	}

	public Class<? extends StringModification> getType() {
		return type;
	}

}
