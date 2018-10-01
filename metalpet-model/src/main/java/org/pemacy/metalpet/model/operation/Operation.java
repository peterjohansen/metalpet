package org.pemacy.metalpet.model.operation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Peter André Johansen
 */
public interface Operation {

	OperationIdentifier getIdentifier();

	String getReport();

}
