package org.pemacy.metalpet.model.operation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * @author Peter André Johansen
 */
public abstract class OperationBase implements Operation {

	@JsonProperty("report")
	@NotBlank(message = "Report is missing, empty or blank.")
	private final String report;

	public OperationBase(String report) {
		this.report = report;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }
		final var other = (OperationBase) o;
		return Objects.equals(report, other.report);
	}

	@Override
	public String getReport() {
		return report;
	}

	@Override
	public int hashCode() {
		return Objects.hash(report);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("report", report)
			.toString();
	}

}
