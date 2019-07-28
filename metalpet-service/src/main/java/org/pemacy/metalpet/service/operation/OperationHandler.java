package org.pemacy.metalpet.service.operation;

import org.pemacy.metalpet.model.operation.Operation;

import java.nio.file.Path;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface OperationHandler<T extends Operation> extends BiConsumer<Path, T> {

	@Override
	void accept(Path rootDirectory, T operation);

}
