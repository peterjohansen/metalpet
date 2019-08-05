package org.pemacy.metalpet.model.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;
import org.pemacy.metalpet.model.operation.Operation;

import javax.validation.constraints.NotBlank;

@Value.Immutable
@JsonSerialize(as = ImmutableReportOperation.class)
@JsonDeserialize(as = ImmutableReportOperation.class)
public interface ReportOperation extends Operation {

	@NotBlank(message = "Report is missing, empty or blank.")
	@JsonProperty("message")
	String getMessage();

}
