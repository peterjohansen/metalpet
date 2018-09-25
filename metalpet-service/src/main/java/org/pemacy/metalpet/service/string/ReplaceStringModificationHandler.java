package org.pemacy.metalpet.service.string;

import org.pemacy.metalpet.model.string.ReplaceStringModification;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Peter André Johansen
 */
public class ReplaceStringModificationHandler implements StringModificationHandler<ReplaceStringModification> {

	@Override
	public String apply(String original, ReplaceStringModification modification) {
		checkNotNull(original);
		checkNotNull(modification);
		return original.replace(modification.getTarget(), modification.getReplacement());
	}

}
