package org.pemacy.metalpet.model.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.pemacy.metalpet.validation.Validatable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
		return Objects.equal(prompt, other.prompt)
			&& Objects.equal(type, other.type)
			&& Objects.equal(variable, other.variable)
			&& Objects.equal(optional, other.optional)
			&& Objects.equal(defaultValue, other.defaultValue);
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
		return Objects.hashCode(prompt, type, variable, optional, defaultValue);
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
