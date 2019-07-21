package org.pemacy.metalpet.model.operation;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum StandardOperationIdentifier implements OperationIdentifier {

	@JsonProperty("delete-files")
	DELETE_FILES,

	@JsonProperty("file-name-search-and-modify")
	FILE_NAME_SEARCH_AND_MODIFY;

}
