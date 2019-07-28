package org.pemacy.metalpet.service.operation;

import org.pemacy.metalpet.model.operation.Operation;

import java.util.Optional;
import java.util.function.Function;

public interface OperationHandlerFunction<T extends Operation>
	extends Function<Class<? extends T>, Optional<OperationHandler>> {

	@Override
	Optional<OperationHandler> apply(Class<? extends T> aClass);

}
