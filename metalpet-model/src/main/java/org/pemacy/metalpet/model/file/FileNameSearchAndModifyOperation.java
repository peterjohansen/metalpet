package org.pemacy.metalpet.model.file;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;
import org.pemacy.metalpet.model.operation.OperationBase;
import org.pemacy.metalpet.model.operation.OperationIdentifier;
import org.pemacy.metalpet.model.operation.StandardOperationIdentifier;
import org.pemacy.metalpet.model.string.StringModification;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.immutables.value.Value.Style.ValidationMethod.VALIDATION_API;

@Value.Immutable
@Value.Style(validationMethod = VALIDATION_API)
@JsonSerialize(as = ImmutableFileNameSearchAndModifyOperation.class)
@JsonDeserialize(as = ImmutableFileNameSearchAndModifyOperation.class)
@JsonIgnoreProperties(value = "type")
@JsonPropertyOrder({ "report", "type", "glob", "ignoreFiles", "ignoreDirectories" })
public interface FileNameSearchAndModifyOperation extends OperationBase {

	@Override
	String getReport();

	@NotEmpty(message = "List of targets cannot be undefined or empty.")
	@JsonProperty("targets")
	List<@NotNull @Valid FileTarget> getTargets();

	@NotEmpty(message = "List of modifications cannot be undefined or empty.")
	@JsonProperty("modifications")
	List<@NotNull @Valid StringModification> getModifications();

	@Override
	default OperationIdentifier getIdentifier() {
		return StandardOperationIdentifier.FILE_NAME_SEARCH_AND_MODIFY;
	}

}
