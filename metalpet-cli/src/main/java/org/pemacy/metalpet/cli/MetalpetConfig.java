package org.pemacy.metalpet.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import org.pemacy.metalpet.json.MetalpetModule;
import org.pemacy.metalpet.model.file.DeleteFilesOperation;
import org.pemacy.metalpet.model.file.MatcherFileTarget;
import org.pemacy.metalpet.model.output.ReportOperation;
import org.pemacy.metalpet.model.string.ReplaceStringModification;
import org.pemacy.metalpet.service.file.DeleteFilesOperationHandler;
import org.pemacy.metalpet.service.file.FileService;
import org.pemacy.metalpet.service.file.FileTargetHandlerFunction;
import org.pemacy.metalpet.service.file.MatcherFileTargetHandler;
import org.pemacy.metalpet.service.operation.OperationHandlerFunction;
import org.pemacy.metalpet.service.output.OutputService;
import org.pemacy.metalpet.service.output.ReportOperationHandler;
import org.pemacy.metalpet.service.string.ReplaceStringModificationHandler;
import org.pemacy.metalpet.service.string.StringModificationHandlerFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

@Configuration
public class MetalpetConfig {

	/**
	 * Returns a handler from a map of types to handlers.
	 * <p>
	 * If the type is not present in the map, the super-interfaces of
	 * the type will be used. This is to accommodate the Immutables
	 * library's implementation. If not handler is found an empty
	 * optional will be returned.
	 */
	private static <Model, Handler> Optional<Handler> getHandler(Class<? extends Model> type,
																 Map<Class<? extends Model>, Handler> handlers) {
		var handler = handlers.get(type);
		if (handler == null) {
			for (Class<?> superInterface  : type.getInterfaces()) {
				handler = handlers.get(superInterface);
				if (handler != null) {
					break;
				}
			}
		}
		return Optional.ofNullable(handler);
	}

	@Bean
	public FileTargetHandlerFunction getFileTargetHandlerFunction() {
		return type -> getHandler(type, Map.of(
			MatcherFileTarget.class, new MatcherFileTargetHandler()
		));
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
	public OperationHandlerFunction getOperationHandlerFunction(OutputService outputService, FileService fileService) {
		checkNotNull(fileService);
		return type -> getHandler(type, Map.of(
			ReportOperation.class, new ReportOperationHandler(outputService),
			DeleteFilesOperation.class, new DeleteFilesOperationHandler(fileService)
		));
	}

	@Bean
	public StringModificationHandlerFunction getStringModificationHandlerFunction() {
		return type -> getHandler(type, Map.of(
			ReplaceStringModification.class, new ReplaceStringModificationHandler()
		));
	}

}
