package org.pemacy.metalpet.service.string.exception;

import org.pemacy.metalpet.model.string.StringModification;

import java.util.Objects;

public class NoSuchStringModificationHandlerException extends RuntimeException {

	private final Class<? extends StringModification> type;

	public NoSuchStringModificationHandlerException(Class<? extends StringModification> type) {
		super("no such string modification handler: " + Objects.requireNonNull(type));
		this.type = type;
	}

	public Class<? extends StringModification> getType() {
		return type;
	}

}
