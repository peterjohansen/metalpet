package org.pemacy.metalpet.service.value;

import org.pemacy.metalpet.model.project.OngoingProject;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class ValueService {

	public <T> T getValue(OngoingProject ongoingProject, String name, Class<T> type) {
		checkNotNull(ongoingProject);
		checkNotNull(name);
		checkNotNull(type);
		checkArgument(!name.isBlank(), "value name cannot be blank");

		final var objectValue = getRawVariable(ongoingProject, name)
			.orElseThrow(RuntimeException::new); // TODO
		if (!type.isInstance(objectValue)) {
			throw new RuntimeException(); // TODO
		}

		return type.cast(objectValue);
	}

	public Optional<Object> getRawVariable(OngoingProject ongoingProject, String name) {
		checkNotNull(ongoingProject);
		checkNotNull(name);
		checkArgument(!name.isBlank(), "variable name cannot be blank");
		return Optional.ofNullable(ongoingProject.getVariables().get(name));
	}

}
