package org.pemacy.metalpet.model.string;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value.Immutable
@JsonSerialize(as = ImmutableReplaceStringModification.class)
@JsonDeserialize(as = ImmutableReplaceStringModification.class)
@JsonPropertyOrder({ "replace", "with" })
public interface ReplaceStringModification extends StringModification {

	@NotBlank(message = "Replace is undefined, empty or blank.")
	@JsonProperty("replace")
	String getTarget();

	@NotNull(message = "With cannot be undefined.")
	@JsonProperty("with")
	String getReplacement();

}
