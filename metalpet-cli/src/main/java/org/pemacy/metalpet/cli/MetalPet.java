package org.pemacy.metalpet.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.pemacy.metalpet.json.MetalpetModule;
import org.pemacy.metalpet.service.ProjectService;

import java.io.IOException;

/**
 * @author Peter André Johansen
 */
public class MetalPet {

	private static final String DEFAULT_PROJECT_FILE = "metalpet.json";

	private static final Option FILE_OPTION = Option.builder("f")
		.longOpt("file")
		.desc("Template project file.")
		.hasArg()
		.type(String.class)
		.build();

	public static void main(String[] args) throws IOException, ParseException {
		final var cli = new DefaultParser().parse(createOptions(), args);
		final var projectService = new ProjectService(createObjectMapper());
		projectService.execute(cli.getOptionValue(FILE_OPTION.getLongOpt(), DEFAULT_PROJECT_FILE));
	}

	private static ObjectMapper createObjectMapper() {
		final var objectMapper = new ObjectMapper();
		objectMapper.registerModules(
			new GuavaModule(),
			new MetalpetModule()
		);
		return objectMapper;
	}

	private static Options createOptions() {
		final var options = new Options();
		options.addOption(FILE_OPTION);
		return options;
	}

}
