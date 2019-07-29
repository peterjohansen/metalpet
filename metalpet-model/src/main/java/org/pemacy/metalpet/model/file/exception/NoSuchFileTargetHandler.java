package org.pemacy.metalpet.model.file.exception;

import org.pemacy.metalpet.model.file.FileTarget;

import java.util.Objects;

public class NoSuchFileTargetHandler extends RuntimeException {

	private final Class<? extends FileTarget> type;

	public NoSuchFileTargetHandler(Class<? extends FileTarget> type) {
		super("no such file target handler: " + Objects.requireNonNull(type));
		this.type = type;
	}

	public Class<? extends FileTarget> getType() {
		return type;
	}

}
