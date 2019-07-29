package org.pemacy.metalpet.service.string;

import org.pemacy.metalpet.model.string.ReplaceStringModification;
import org.springframework.stereotype.Component;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class ReplaceStringModificationHandler implements StringModificationHandler<ReplaceStringModification> {

	@Override
	public String apply(String original, ReplaceStringModification modification) {
		checkNotNull(original);
		checkNotNull(modification);
		return original.replace(modification.getTarget(), modification.getReplacement());
	}

}
