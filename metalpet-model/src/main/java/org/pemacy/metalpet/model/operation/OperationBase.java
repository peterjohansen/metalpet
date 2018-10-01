package org.pemacy.metalpet.model.operation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.validation.constraints.NotBlank;

/**
 * @author Peter André Johansen
 */
@JsonPropertyOrder({ "report" })
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
		return Objects.equal(report, other.report);
	}

	@Override
	public String getReport() {
		return report;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(report);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("report", report)
			.toString();
	}

}
