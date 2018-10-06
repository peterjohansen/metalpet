package org.pemacy.metalpet.model.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.MoreObjects;
import org.pemacy.metalpet.validation.Validatable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author Peter André Johansen
 */
@JsonPropertyOrder({ "prompt", "type", "variable", "optional", "defaultValue" })
public class UserInput implements Validatable {

	private static final boolean DEFAULT_OPTIONAL = false;
	private static final String DEFAULT_DEFAULT_VALUE = null;

	@JsonProperty("prompt")
	@NotBlank(message = "User input prompt is missing, empty or blank.")
	private final String prompt;

	@JsonProperty("type")
	@NotNull(message = "User input type cannot be undefined.")
	private final InputType type;

	@JsonProperty("variable")
	@NotBlank(message = "User input variable is missing, empty or blank.")
	private final String variable;

	@JsonProperty("optional")
	private final boolean optional;

	@JsonProperty("defaultValue")
	private final String defaultValue;

	public UserInput(String prompt, InputType type, String variable, Boolean optional, String defaultValue) {
		this.prompt = prompt;
		this.type = type;
		this.variable = variable;
		this.optional = (optional != null ? optional : DEFAULT_OPTIONAL);
		this.defaultValue = (defaultValue != null ? defaultValue : DEFAULT_DEFAULT_VALUE);
		validateAfterConstruction();
	}

	/**
	 * For instantiation by reflection.
	 */
	private UserInput() {
		this.prompt = null;
		this.type = null;
		this.variable = null;
		this.optional = DEFAULT_OPTIONAL;
		this.defaultValue = DEFAULT_DEFAULT_VALUE;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }
		final var other = (UserInput) o;
		return Objects.equals(prompt, other.prompt)
			&& Objects.equals(type, other.type)
			&& Objects.equals(variable, other.variable)
			&& Objects.equals(optional, other.optional)
			&& Objects.equals(defaultValue, other.defaultValue);
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public String getPrompt() {
		return prompt;
	}

	public String getVariable() {
		return variable;
	}

	@Override
	public int hashCode() {
		return Objects.hash(prompt, type, variable, optional, defaultValue);
	}

	public boolean isOptional() {
		return optional;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("type", type)
			.add("variable", variable)
			.add("optional", optional)
			.add("defaultValue", defaultValue)
			.toString();
	}

}
