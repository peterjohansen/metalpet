package org.pemacy.metalpet.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import org.pemacy.metalpet.model.file.DeleteFilesOperation;
import org.pemacy.metalpet.model.file.FileNameSearchAndModifyOperation;
import org.pemacy.metalpet.model.file.FileTarget;
import org.pemacy.metalpet.model.file.MatcherFileTarget;
import org.pemacy.metalpet.model.input.InputType;
import org.pemacy.metalpet.model.input.StandardInputType;
import org.pemacy.metalpet.model.operation.Operation;
import org.pemacy.metalpet.model.operation.OperationIdentifier;
import org.pemacy.metalpet.model.operation.StandardOperationIdentifier;
import org.pemacy.metalpet.model.string.ReplaceStringModification;
import org.pemacy.metalpet.model.string.StringModification;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class MetalpetModule extends SimpleModule {

	private static final String NAME = "MetalpetModule";

	private static final Map<OperationIdentifier, Class<? extends Operation>> OPERATION_MAP = Map.of(
		StandardOperationIdentifier.DELETE_FILES, DeleteFilesOperation.class,
		StandardOperationIdentifier.FILE_NAME_SEARCH_AND_MODIFY, FileNameSearchAndModifyOperation.class
	);

	private final SimpleAbstractTypeResolver abstractTypeResolver = new SimpleAbstractTypeResolver();

	public MetalpetModule() {
		super(NAME);
		setUpFiles();
		setUpOperations();
		setUpStringModifications();
		setUpUserInputs();
		setAbstractTypes(abstractTypeResolver);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }
		final var other = (MetalpetModule) o;
		return Objects.equals(getModuleName(), other.getModuleName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getModuleName());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("name", getModuleName())
			.toString();
	}

	private <T extends Enum<T>> Optional<T> enumFromJsonProperty(Class<T> type, T[] values, String jsonProperty) {
		return Arrays.stream(values)
			.filter(enumObject -> {
				final var foundJsonProperty = jsonPropertyFromField(type, enumObject.name());
				return foundJsonProperty.isPresent() && foundJsonProperty.get().equals(jsonProperty);
			})
			.findFirst();
	}

	private Optional<String> jsonPropertyFromField(Class<?> type, String fieldName) {
		try {
			final var field = type.getField(fieldName);
			return Arrays.stream(field.getDeclaredAnnotations())
				.filter(JsonProperty.class::isInstance)
				.map(JsonProperty.class::cast)
				.map(JsonProperty::value)
				.findFirst();
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e); // TODO This is an unexpected case
		}
	}

	private void setUpFiles() {
		addDeserializer(FileTarget.class,
			new PropertyMatchingDeserializer<>(FileTarget.class, ImmutableSet.of(
				MatcherFileTarget.class
			))
		);
	}

	private void setUpOperations() {
		addDeserializer(Operation.class, new TypeSpecificationDeserializer<>(Operation.class, "type", id -> {
			final var identifier = enumFromJsonProperty(
				StandardOperationIdentifier.class,
				StandardOperationIdentifier.values(),
				id
			).orElseGet(() -> StandardOperationIdentifier.valueOf(id));
			return OPERATION_MAP.get(identifier);
		}));
	}

	private void setUpStringModifications() {
		addDeserializer(StringModification.class,
			new PropertyMatchingDeserializer<>(StringModification.class, ImmutableSet.of(
				ReplaceStringModification.class
			))
		);
	}

	private void setUpUserInputs() {
		abstractTypeResolver.addMapping(InputType.class, StandardInputType.class);
	}

}
