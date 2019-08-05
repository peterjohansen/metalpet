package org.pemacy.metalpet.model.file;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value.Immutable
@JsonSerialize(as = ImmutableMatcherFileTarget.class)
@JsonDeserialize(as = ImmutableMatcherFileTarget.class)
@JsonPropertyOrder({ "glob", "ignore" })
public interface MatcherFileTarget extends FileTarget {

	FileTargetIgnore DEFAULT_IGNORE = FileTargetIgnore.NONE;

	@NotBlank(message = "Glob is undefined, empty or blank.")
	@JsonProperty("glob")
	String getGlob();

	@Value.Default
	@NotNull(message = "Ignore cannot be undefined.")
	@JsonProperty("ignore")
	default FileTargetIgnore getIgnore() {
		return DEFAULT_IGNORE;
	}

}
