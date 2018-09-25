package org.pemacy.metalpet.model.operation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Peter André Johansen
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Operation {

	OperationIdentifier getIdentifier();

	String getReport();

}
