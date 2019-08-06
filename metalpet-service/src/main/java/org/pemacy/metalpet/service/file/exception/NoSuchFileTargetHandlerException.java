package org.pemacy.metalpet.service.file.exception;

import org.pemacy.metalpet.model.file.FileTarget;

import static com.google.common.base.Preconditions.checkNotNull;

public class NoSuchFileTargetHandlerException extends RuntimeException {

	private final Class<? extends FileTarget> type;

	public NoSuchFileTargetHandlerException(Class<? extends FileTarget> type) {
		super("no such file target handler: " + checkNotNull(type));
		this.type = type;
	}

	public Class<? extends FileTarget> getType() {
		return type;
	}

}
