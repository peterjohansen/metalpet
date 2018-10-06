package org.pemacy.metalpet.model.operation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import org.pemacy.metalpet.model.file.target.matcher.MatcherFileTarget;
import org.pemacy.metalpet.validation.Validatable;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * @author Peter André Johansen
 */
@JsonIgnoreProperties({ "type" })
@JsonPropertyOrder({ "report", "type", "targets" })
public class DeleteFilesOperation extends OperationBase implements Validatable {

	@JsonProperty("targets")
	@NotEmpty(message = "List of targets cannot be undefined or empty.")
	private final ImmutableList<@NotNull @Valid MatcherFileTarget> targets;

	public DeleteFilesOperation(String report, List<MatcherFileTarget> targets) {
		super(report);
		this.targets = ImmutableList.copyOf(targets);
		validateAfterConstruction();
	}

	/**
	 * For instantiation by reflection.
	 */
	private DeleteFilesOperation() {
		super(null);
		this.targets = null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }
		final var other = (DeleteFilesOperation) o;
		return Objects.equals(targets, other.targets);
	}

	@Override
	public OperationIdentifier getIdentifier() {
		return StandardOperationIdentifier.DELETE_FILES;
	}

	public ImmutableList<MatcherFileTarget> getTargets() {
		return targets;
	}

	@Override
	public int hashCode() {
		return Objects.hash(targets);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("targets", targets)
			.toString();
	}

}
