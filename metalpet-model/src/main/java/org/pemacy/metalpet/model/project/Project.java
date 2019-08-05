package org.pemacy.metalpet.model.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableList;
import org.immutables.value.Value;
import org.pemacy.metalpet.model.input.UserInput;
import org.pemacy.metalpet.model.operation.Operation;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Value.Immutable
@JsonSerialize(as = ImmutableProject.class)
@JsonDeserialize(as = ImmutableProject.class)
@JsonPropertyOrder({ "name", "projectFile", "input", "operation" })
public interface Project {

	List<UserInput> DEFAULT_USER_INPUT_LIST = ImmutableList.of();
	List<Operation> DEFAULT_OPERATIONS = ImmutableList.of();

	@NotBlank(message = "Project name is undefined, empty or blank.")
	@JsonProperty("name")
	String getName();

	@JsonProperty("projectFile")
	Optional<Path> getProjectFile();

	@Value.Default
	@NotNull(message = "List of user inputs in the project cannot be undefined.")
	@JsonProperty("input")
	default List<@NotNull @Valid UserInput> getUserInputList() {
		return DEFAULT_USER_INPUT_LIST;
	}

	@Value.Default
	@NotNull(message = "List of operations in the project cannot be undefined.")
	@JsonProperty("operations")
	default List<@NotNull @Valid Operation> getOperations() {
		return DEFAULT_OPERATIONS;
	}

}
