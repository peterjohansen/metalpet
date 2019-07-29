package org.pemacy.metalpet.service.operation;

import org.pemacy.metalpet.model.operation.Operation;
import org.pemacy.metalpet.model.operation.exception.NoSuchOperationHandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class OperationService {

	private final OperationHandlerFunction operationHandlerFunction;

	@Autowired
	public OperationService(OperationHandlerFunction operationHandlerFunction) {
		this.operationHandlerFunction = checkNotNull(operationHandlerFunction);
	}

	@SuppressWarnings("unchecked")
	public void perform(Path rootDirectory, Operation operation) {
		checkNotNull(rootDirectory);
		checkNotNull(operation);

		final var handler = operationHandlerFunction.apply(operation.getClass())
			.orElseThrow(() -> new NoSuchOperationHandlerException(operation.getClass()));
		handler.accept(rootDirectory, operation);
	}

}
