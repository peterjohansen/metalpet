package org.pemacy.metalpet.model.string;

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
@JsonPropertyOrder({ "replace", "with" })
public class ReplaceStringModification implements StringModification, Validatable {

	@JsonProperty("replace")
	@NotBlank(message = "Replace is missing, empty or blank.")
	private final String target;

	@JsonProperty("with")
	@NotNull(message = "With cannot be undefined.")
	private final String replacement;

	public ReplaceStringModification(String target, String replacement) {
		this.target = target;
		this.replacement = replacement;
		validateAfterConstruction();
	}

	/**
	 * For instantiation by reflection.
	 */
	private ReplaceStringModification() {
		this.target = null;
		this.replacement = null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }
		final var other = (ReplaceStringModification) o;
		return Objects.equals(target, other.target)
			&& Objects.equals(replacement, other.replacement);
	}

	public String getReplacement() {
		return replacement;
	}

	public String getTarget() {
		return target;
	}

	@Override
	public int hashCode() {
		return Objects.hash(target, replacement);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("target", target)
			.add("replacement", replacement)
			.toString();
	}

}
