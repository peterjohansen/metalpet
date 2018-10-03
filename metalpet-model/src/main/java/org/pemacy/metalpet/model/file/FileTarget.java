package org.pemacy.metalpet.model.file;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.pemacy.metalpet.validation.Validatable;

import javax.validation.constraints.NotBlank;

/**
 * @author Peter André Johansen
 */
@JsonPropertyOrder({ "glob", "ignore" })
public class FileTarget implements Validatable {

	private static final FileTargetIgnore DEFAULT_IGNORE = FileTargetIgnore.NONE;

	@JsonProperty("glob")
	@NotBlank(message = "Glob is missing, empty or blank.")
	private final String glob;

	@JsonProperty("ignore")
	private final FileTargetIgnore ignore;

	public FileTarget(String glob, FileTargetIgnore ignore) {
		this.glob = glob;
		this.ignore = (ignore != null ? ignore : DEFAULT_IGNORE);
		validateAfterConstruction();
	}

	/**
	 * For instantiation by reflection.
	 */
	private FileTarget() {
		this.glob = null;
		this.ignore = DEFAULT_IGNORE;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }
		final var other = (FileTarget) o;
		return Objects.equal(glob, other.glob)
			&& Objects.equal(ignore, other.ignore);
	}

	public String getGlob() {
		return glob;
	}

	public FileTargetIgnore getIgnore() {
		return ignore;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(glob, ignore);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("glob", glob)
			.add("ignore", ignore)
			.toString();
	}

}
