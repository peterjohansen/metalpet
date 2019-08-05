
@Value.Style(
	get = { "get*", "is*" },
	// typeImmutable = "*", // No prefix or suffix for generated immutable type TODO Try this
	validationMethod = ValidationMethod.VALIDATION_API
)
package org.pemacy.metalpet.model;

import org.immutables.value.Value;
import org.immutables.value.Value.Style.ValidationMethod;
