package org.pemacy.metalpet.model.operation;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum StandardOperationIdentifier implements OperationIdentifier {

	@JsonProperty("DELETE_FILES")
	DELETE_FILES,

	@JsonProperty("FILE_NAME_SEARCH_AND_MODIFY")
	FILE_NAME_SEARCH_AND_MODIFY

}
