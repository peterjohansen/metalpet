package org.pemacy.metalpet.cli;

import org.immutables.value.Value;

import javax.validation.constraints.NotNull;
import java.nio.file.Path;

import static org.immutables.value.Value.Style;
import static org.immutables.value.Value.Style.ValidationMethod.VALIDATION_API;

@Value.Immutable
@Style(validationMethod = VALIDATION_API)
public interface MetalpetArguments {

	@NotNull
	Path getProjectFilePath();

}
