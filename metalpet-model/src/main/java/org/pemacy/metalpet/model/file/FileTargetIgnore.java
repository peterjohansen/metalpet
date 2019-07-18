package org.pemacy.metalpet.model.file;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FileTargetIgnore {

	@JsonProperty("none")
	NONE,

	@JsonProperty("files")
	FILES,

	@JsonProperty("directories")
	DIRECTORIES

}
