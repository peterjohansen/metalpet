package org.pemacy.metalpet.model.operation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import org.pemacy.metalpet.model.file.FileTarget;
import org.pemacy.metalpet.validation.Validatable;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Peter André Johansen
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "targets" })
public class DeleteDirectoryOperation extends OperationBase implements Validatable {

	@JsonProperty("targets")
	@NotEmpty(message = "List of targets cannot be undefined or empty.")
	private final ImmutableList<@NotNull @Valid FileTarget> targets;

	public DeleteDirectoryOperation(String report, List<FileTarget> targets) {
		super(report);
		this.targets = ImmutableList.copyOf(targets);
		validateAfterConstruction();
	}

	/**
	 * For instantiation by reflection.
	 */
	private DeleteDirectoryOperation() {
		super(null);
		this.targets = null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }
		final var other = (DeleteDirectoryOperation) o;
		return Objects.equal(targets, other.targets);
	}

	@Override
	public OperationIdentifier getIdentifier() {
		return StandardOperationIdentifier.DELETE_DIRECTORY;
	}

	public ImmutableList<FileTarget> getTargets() {
		return targets;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(targets);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("targets", targets)
			.toString();
	}

}
