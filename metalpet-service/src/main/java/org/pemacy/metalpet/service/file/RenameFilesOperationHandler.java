package org.pemacy.metalpet.service.file;

import org.pemacy.metalpet.model.file.RenameFilesOperation;
import org.pemacy.metalpet.service.operation.OperationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class RenameFilesOperationHandler implements OperationHandler<RenameFilesOperation> {

	private static final Logger LOGGER = LoggerFactory.getLogger(RenameFilesOperationHandler.class);

	private final FileService fileService;

	@Autowired
	public RenameFilesOperationHandler(FileService fileService) {
		this.fileService = checkNotNull(fileService);
	}

	@Override
	public void accept(Path rootDirectory, RenameFilesOperation operation) {
		checkNotNull(rootDirectory);
		checkNotNull(operation);

		LOGGER.debug("Running with parameters: root directory = {}, operation = {}", rootDirectory, operation);
	}

}
