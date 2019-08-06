package org.pemacy.metalpet.service.output;

import org.pemacy.metalpet.model.output.ReportOperation;
import org.pemacy.metalpet.service.operation.OperationHandler;

import java.nio.file.Path;

import static com.google.common.base.Preconditions.checkNotNull;

public class ReportOperationHandler implements OperationHandler<ReportOperation> {

	private final OutputService outputService;

	public ReportOperationHandler(OutputService outputService) {
		this.outputService = checkNotNull(outputService);
	}

	@Override
	public void accept(Path rootDirectory, ReportOperation operation) {
		checkNotNull(rootDirectory);
		checkNotNull(operation);
		outputService.printf("%s...", operation.getMessage());
	}

}
