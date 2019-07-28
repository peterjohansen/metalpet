package org.pemacy.metalpet.model.operation;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public interface Operation {

	OperationIdentifier getIdentifier();

	@NotBlank(message = "Report is missing, empty or blank.")
	@JsonProperty("report")
	String getReport();

}
