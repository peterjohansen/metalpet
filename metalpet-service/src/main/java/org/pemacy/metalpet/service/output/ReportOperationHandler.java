package org.pemacy.metalpet.service.output;

import org.pemacy.metalpet.model.output.ReportOperation;
import org.pemacy.metalpet.service.operation.OperationHandler;

import java.nio.file.Path;
import java.util.Objects;

public class ReportOperationHandler implements OperationHandler<ReportOperation> {

	private final OutputService outputService;

	public ReportOperationHandler(OutputService outputService) {
		this.outputService = Objects.requireNonNull(outputService);
	}

	@Override
	public void accept(Path rootDirectory, ReportOperation operation) {
		Objects.requireNonNull(rootDirectory);
		Objects.requireNonNull(operation);
		outputService.printf("%s...", operation.getMessage());
	}

}
