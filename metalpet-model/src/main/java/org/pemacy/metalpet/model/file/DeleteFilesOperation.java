package org.pemacy.metalpet.model.file;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;
import org.pemacy.metalpet.model.operation.Operation;
import org.pemacy.metalpet.model.operation.OperationIdentifier;
import org.pemacy.metalpet.model.operation.StandardOperationIdentifier;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutableDeleteFilesOperation.class)
@JsonDeserialize(as = ImmutableDeleteFilesOperation.class)
@JsonIgnoreProperties(value = "type")
@JsonPropertyOrder({ "report", "type", "targets" })
public interface DeleteFilesOperation extends Operation {

	@NotEmpty(message = "List of targets cannot be undefined or empty.")
	@JsonProperty("targets")
	List<@NotNull @Valid FileTarget> getTargets();

	@Override
	default OperationIdentifier getIdentifier() {
		return StandardOperationIdentifier.DELETE_FILES;
	}

}
