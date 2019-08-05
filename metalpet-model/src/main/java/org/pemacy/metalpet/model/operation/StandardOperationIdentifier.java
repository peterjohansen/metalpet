package org.pemacy.metalpet.model.operation;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum StandardOperationIdentifier implements OperationIdentifier {

	@JsonProperty("report")
	REPORT,

	@JsonProperty("delete-files")
	DELETE_FILES,

	@JsonProperty("rename-files")
	RENAME_FILES;

}
