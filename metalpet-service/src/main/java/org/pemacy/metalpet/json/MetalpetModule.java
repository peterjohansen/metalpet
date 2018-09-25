package org.pemacy.metalpet.json;

import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.pemacy.metalpet.model.input.InputType;
import org.pemacy.metalpet.model.input.StandardInputType;
import org.pemacy.metalpet.model.operation.DeleteDirectoryOperation;
import org.pemacy.metalpet.model.operation.FileNameSearchAndModifyOperation;
import org.pemacy.metalpet.model.operation.Operation;
import org.pemacy.metalpet.model.operation.OperationIdentifier;
import org.pemacy.metalpet.model.operation.StandardOperationIdentifier;
import org.pemacy.metalpet.model.string.ReplaceStringModification;
import org.pemacy.metalpet.model.string.StringModification;

/**
 * @author Peter André Johansen
 */
public class MetalpetModule extends SimpleModule {

	private static final String NAME = "MetalpetModule";

	private static final ImmutableMap<OperationIdentifier, Class<? extends Operation>> OPERATION_MAP = ImmutableMap.of(
		StandardOperationIdentifier.DELETE_DIRECTORY, DeleteDirectoryOperation.class,
		StandardOperationIdentifier.FILE_NAME_SEARCH_AND_MODIFY, FileNameSearchAndModifyOperation.class
	);

	private final SimpleAbstractTypeResolver abstractTypeResolver = new SimpleAbstractTypeResolver();

	public MetalpetModule() {
		super(NAME);
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
		return Objects.equal(getModuleName(), other.getModuleName());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getModuleName());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("name", getModuleName())
			.toString();
	}

	private void setUpOperations() {
		addDeserializer(Operation.class, new TypeSpecificationDeserializer<>(Operation.class, "type", id ->
			OPERATION_MAP.get(StandardOperationIdentifier.valueOf(id))
		));
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
