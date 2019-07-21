package org.pemacy.metalpet.model.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

import static org.immutables.value.Value.Style.ValidationMethod.VALIDATION_API;

@Value.Immutable
@Value.Style(validationMethod = VALIDATION_API)
@JsonSerialize(as = ImmutableUserInput.class)
@JsonDeserialize(as = ImmutableUserInput.class)
@JsonPropertyOrder({ "prompt", "type", "variable", "optional", "defaultValue" })
public interface UserInput {

	@NotBlank(message = "User input prompt is undefined, empty or blank.")
	@JsonProperty("prompt")
	String getPrompt();

	@NotNull(message = "User input type cannot be undefined.")
	@JsonProperty("type")
	InputType getType();

	@NotBlank(message = "User input variable is undefined, empty or blank.")
	@JsonProperty("variable")
	String getVariable();

	@JsonProperty("defaultValue")
	Optional<String> getDefaultValue();

}
