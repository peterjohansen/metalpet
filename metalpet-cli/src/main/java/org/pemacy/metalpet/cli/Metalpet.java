package org.pemacy.metalpet.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.pemacy.metalpet.json.MetalpetModule;
import org.pemacy.metalpet.model.ExecutionStep;
import org.pemacy.metalpet.output.OutputService;
import org.pemacy.metalpet.service.ProjectService;
import org.pemacy.metalpet.service.input.InputService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Metalpet {

	private static final Logger LOGGER = LoggerFactory.getLogger(Metalpet.class);

	private static final String DEFAULT_PROJECT_FILE = "metalpet.json";

	private static final Option FILE_OPTION = Option.builder("f")
		.argName("file")
		.optionalArg(true)
		.desc("Template project file.")
		.type(String.class)
		.build();

	public static void main(String[] args) throws IOException, ParseException {
		final var cli = new DefaultParser().parse(createOptions(), args);
		final var projectService = new ProjectService(createInputService(), createOutputService(), createObjectMapper());
		final var projectFile = cli.getArgList().isEmpty() ? DEFAULT_PROJECT_FILE : cli.getArgList().get(0);
		final var projectModel = projectService.parseProject(projectFile);
		final var ongoingProject = projectService.startProject(projectModel);
		final var finishedProject = projectService.execute(ongoingProject);
		assert(finishedProject.getCurrentExecutionStep() == ExecutionStep.FINISHED);
	}

	private static InputService createInputService() {
		return new InputService();
	}

	private static ObjectMapper createObjectMapper() {
		final var objectMapper = new ObjectMapper();
		objectMapper.registerModules(
			new GuavaModule(),
			new MetalpetModule()
		);
		return objectMapper;
	}

	private static OutputService createOutputService() {
		return System.out::print;
	}

	private static Options createOptions() {
		final var options = new Options();
		options.addOption(FILE_OPTION);
		return options;
	}

}
