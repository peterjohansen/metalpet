package org.pemacy.metalpet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import org.pemacy.metalpet.model.input.UserInput;
import org.pemacy.metalpet.model.operation.Operation;
import org.pemacy.metalpet.validation.Validatable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

/**
 * @author Peter André Johansen
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "name", "input", "operation" })
public class Project implements Validatable {

	private static final ImmutableList<UserInput> DEFAULT_USER_INPUT_LIST = ImmutableList.copyOf(Collections.emptyList());
	private static final ImmutableList<Operation> DEFAULT_OPERATIONS = ImmutableList.copyOf(Collections.emptyList());

	@JsonProperty("name")
	@NotBlank(message = "Project name is missing, empty or blank.")
	private final String name;

	@JsonProperty("input")
	@NotNull(message = "List of user inputs in the project cannot be undefined.")
	private final ImmutableList<@NotNull @Valid UserInput> userInputList;

	@JsonProperty("operations")
	@NotNull(message = "List of operations in the project cannot be undefined.")
	private final ImmutableList<@NotNull @Valid Operation> operations;

	public Project(String name, List<UserInput> userInputList, List<Operation> operations) {
		this.name = name;
		this.userInputList = (userInputList != null ? ImmutableList.copyOf(userInputList) : DEFAULT_USER_INPUT_LIST);
		this.operations = (operations != null ? ImmutableList.copyOf(operations) : DEFAULT_OPERATIONS);
		validateAfterConstruction();
	}

	/**
	 * For instantiation by reflection.
	 */
	private Project() {
		this.name = null;
		this.userInputList = DEFAULT_USER_INPUT_LIST;
		this.operations = DEFAULT_OPERATIONS;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }
		final var other = (Project) o;
		return Objects.equal(name, other.name)
			&& Objects.equal(userInputList, other.userInputList)
			&& Objects.equal(operations, other.operations);
	}

	public String getName() {
		return name;
	}

	public ImmutableList<Operation> getOperations() {
		return operations;
	}

	public ImmutableList<UserInput> getUserInputList() {
		return userInputList;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("name", name)
			.add("userInputList", userInputList)
			.add("operations", operations)
			.toString();
	}

}
