package org.pemacy.metalpet.model.input;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum StandardInputType implements InputType {

	@JsonProperty("string")
	STRING,

	@JsonProperty("boolean")
	BOOLEAN,

	@JsonProperty("integer")
	INTEGER,

	@JsonProperty("decimal")
	DECIMAL,

}
