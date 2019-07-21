package org.pemacy.metalpet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.immutables.value.Value.Style.ValidationMethod.VALIDATION_API;

@Value.Immutable
@Value.Style(validationMethod = VALIDATION_API)
@JsonSerialize(as = ImmutableOngoingProject.class)
@JsonDeserialize(as = ImmutableOngoingProject.class)
@JsonPropertyOrder({ "projectModel", "executionListener", "currentExecutionStep" })
public interface OngoingProject {

	@Value.Parameter
	@NotNull(message = "Project model cannot be undefined.")
	@JsonProperty("projectModel")
	Project getProjectModel();

	@JsonProperty("executionListener")
	Optional<ProjectExecutionListener> getExecutionListener();

	@Value.Default
	@JsonProperty("currentExecutionStep")
	@NotNull(message = "Current execution step cannot be undefined.")
	default ExecutionStep getCurrentExecutionStep() {
		return ExecutionStep.DISPLAYING_PROJECT_INFO;
	}

	@Value.Default
	@JsonProperty("variables")
	@NotNull(message = "Variables cannot be null.")
	default Map<String, Object> getVariables() {
		return new HashMap<>();
	}

}
