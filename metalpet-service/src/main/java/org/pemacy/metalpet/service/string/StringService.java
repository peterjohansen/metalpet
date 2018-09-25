package org.pemacy.metalpet.service.string;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.pemacy.metalpet.model.string.StringModification;

import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Peter André Johansen
 */
public class StringService {

	private final ImmutableMap<Class<? extends StringModification>, StringModificationHandler> handlers;

	public StringService(Map<Class<? extends StringModification>, StringModificationHandler> handlers) {
		checkNotNull(handlers);
		handlers.keySet().forEach(Preconditions::checkNotNull);
		handlers.values().forEach(Preconditions::checkNotNull);
		this.handlers = ImmutableMap.copyOf(handlers);
	}

	public ImmutableMap<Class<? extends StringModification>, StringModificationHandler> getHandlers() {
		return handlers;
	}

	@SuppressWarnings("unchecked")
	public String process(String original, StringModification modification) {
		checkNotNull(original);
		checkNotNull(modification);
		checkArgument(handlers.containsKey(modification.getClass()),
			"no handler for string modification: " + modification.getClass().getName());
		return handlers.get(modification.getClass()).apply(original, modification);
	}

}
