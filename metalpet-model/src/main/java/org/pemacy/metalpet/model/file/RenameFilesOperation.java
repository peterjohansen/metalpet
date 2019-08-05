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
import org.pemacy.metalpet.model.string.StringModification;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutableRenameFilesOperation.class)
@JsonDeserialize(as = ImmutableRenameFilesOperation.class)
@JsonIgnoreProperties(value = "type")
@JsonPropertyOrder({ "report", "targets", "modifications" })
public interface RenameFilesOperation extends Operation {

	@NotEmpty(message = "List of targets cannot be undefined or empty.")
	@JsonProperty("targets")
	List<@NotNull @Valid FileTarget> getTargets();

	@NotEmpty(message = "List of modifications cannot be undefined or empty.")
	@JsonProperty("modifications")
	List<@NotNull @Valid StringModification> getModifications();

	@Override
	default OperationIdentifier getIdentifier() {
		return StandardOperationIdentifier.RENAME_FILES;
	}

}
