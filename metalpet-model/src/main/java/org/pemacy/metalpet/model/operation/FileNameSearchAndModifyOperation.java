package org.pemacy.metalpet.model.operation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import org.pemacy.metalpet.model.file.FileTarget;
import org.pemacy.metalpet.model.string.StringModification;
import org.pemacy.metalpet.validation.Validatable;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @author Peter André Johansen
 */
@JsonIgnoreProperties({ "type" })
@JsonPropertyOrder({ "report", "type", "glob", "ignoreFiles", "ignoreDirectories" })
public class FileNameSearchAndModifyOperation extends OperationBase implements Validatable {

	@JsonProperty("targets")
	@NotEmpty(message = "List of targets cannot be undefined or empty.")
	private final ImmutableList<@NotNull @Valid FileTarget> targets;

	@JsonProperty("modifications")
	@NotEmpty(message = "List of modifications cannot be undefined or empty.")
	private final ImmutableList<@NotNull @Valid StringModification> modifications;

	public FileNameSearchAndModifyOperation(String report,
											List<FileTarget> targets,
											List<StringModification> modifications) {
		super(report);
		this.targets = ImmutableList.copyOf(targets);
		this.modifications = ImmutableList.copyOf(modifications);
		validateAfterConstruction();
	}

	/**
	 * For instantiation by reflection.
	 */
	private FileNameSearchAndModifyOperation() {
		super(null);
		this.targets = null;
		this.modifications = null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }
		final var other = (FileNameSearchAndModifyOperation) o;
		return Objects.equal(targets, other.targets)
			&& Objects.equal(modifications, other.modifications);
	}

	@Override
	public OperationIdentifier getIdentifier() {
		return StandardOperationIdentifier.FILE_NAME_SEARCH_AND_MODIFY;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(targets, modifications);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("targets", targets)
			.add("modifications", modifications)
			.toString();
	}

}
