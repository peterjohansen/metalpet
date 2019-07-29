package org.pemacy.metalpet.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import org.pemacy.metalpet.json.MetalpetModule;
import org.pemacy.metalpet.model.file.DeleteFilesOperation;
import org.pemacy.metalpet.model.file.MatcherFileTarget;
import org.pemacy.metalpet.model.file.exception.NoSuchFileTargetHandler;
import org.pemacy.metalpet.model.operation.exception.NoSuchOperationHandlerException;
import org.pemacy.metalpet.model.string.ReplaceStringModification;
import org.pemacy.metalpet.model.string.exception.NoSuchStringModificationHandlerException;
import org.pemacy.metalpet.service.file.*;
import org.pemacy.metalpet.service.operation.OperationHandler;
import org.pemacy.metalpet.service.operation.OperationHandlerFunction;
import org.pemacy.metalpet.service.string.ReplaceStringModificationHandler;
import org.pemacy.metalpet.service.string.StringModificationHandler;
import org.pemacy.metalpet.service.string.StringModificationHandlerFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;
import java.util.Optional;

@Configuration
public class MetalpetConfig {

	@Bean
	public FileTargetHandlerFunction getFileTargetHandlerFunction() {
		return type -> {
			FileTargetHandler<?> handler;
			if (type == MatcherFileTarget.class) handler = new MatcherFileTargetHandler();
			else throw new NoSuchFileTargetHandler(type);
			return Optional.of(handler);
		};
	}

	@Bean
	public ObjectMapper getObjectMapper() {
		final var objectMapper = new ObjectMapper();
		objectMapper.registerModules(
			new GuavaModule(),
			new MetalpetModule()
		);
		return objectMapper;
	}

	@Bean
	public OperationHandlerFunction getOperationHandlerFunction(FileService fileService) {
		Objects.requireNonNull(fileService);
		return type -> {
			OperationHandler<?> handler;
			if (type == DeleteFilesOperation.class) handler = new DeleteFilesOperationHandler(fileService);
			else throw new NoSuchOperationHandlerException(type);
			return Optional.of(handler);
		};
	}

	@Bean
	public StringModificationHandlerFunction getStringModificationHandlerFunction() {
		return type -> {
			StringModificationHandler<?> handler;
			if (type == ReplaceStringModification.class) handler = new ReplaceStringModificationHandler();
			else throw new NoSuchStringModificationHandlerException(type);
			return Optional.of(handler);
		};
	}

}
