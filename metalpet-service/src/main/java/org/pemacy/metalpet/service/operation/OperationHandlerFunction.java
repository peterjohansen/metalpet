package org.pemacy.metalpet.service.operation;

import org.pemacy.metalpet.model.operation.Operation;

import java.util.Optional;
import java.util.function.Function;

public interface OperationHandlerFunction extends Function<Class<? extends Operation>, Optional<OperationHandler>> {

	@Override
	Optional<OperationHandler> apply(Class<? extends Operation> type);

}
