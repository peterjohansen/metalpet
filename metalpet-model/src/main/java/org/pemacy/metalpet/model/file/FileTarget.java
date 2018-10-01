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
@JsonPropertyOrder({ "glob", "ignoreFiles", "ignoreDirectories" })
public class FileTarget implements Validatable {

	private static final boolean DEFAULT_IGNORE_FILES = false;
	private static final boolean DEFAULT_IGNORE_DIRECTORIES = false;

	@JsonProperty("glob")
	@NotBlank(message = "Glob is missing, empty or blank.")
	private final String glob;

	@JsonProperty("ignoreFiles")
	private final boolean ignoreFiles;

	@JsonProperty("ignoreDirectories")
	private final boolean ignoreDirectories;

	public FileTarget(String glob, Boolean ignoreFiles, Boolean ignoreDirectories) {
		this.glob = glob;
		this.ignoreFiles = (ignoreFiles != null ? ignoreFiles : DEFAULT_IGNORE_FILES);
		this.ignoreDirectories = (ignoreDirectories != null ? ignoreDirectories : DEFAULT_IGNORE_DIRECTORIES);
		validateAfterConstruction();
	}

	/**
	 * For instantiation by reflection.
	 */
	private FileTarget() {
		this.glob = null;
		this.ignoreFiles = DEFAULT_IGNORE_FILES;
		this.ignoreDirectories = DEFAULT_IGNORE_DIRECTORIES;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }
		final var other = (FileTarget) o;
		return Objects.equal(glob, other.glob)
			&& Objects.equal(ignoreFiles, other.ignoreFiles)
			&& Objects.equal(ignoreDirectories, other.ignoreDirectories);
	}

	public String getGlob() {
		return glob;
	}

	public boolean isIgnoreDirectories() {
		return ignoreDirectories;
	}

	public boolean isIgnoreFiles() {
		return ignoreFiles;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(glob, ignoreFiles, ignoreDirectories);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("glob", glob)
			.add("ignoreFiles", ignoreFiles)
			.add("ignoreDirectories", ignoreDirectories)
			.toString();
	}

}
