package org.pemacy.metalpet.cli;

import org.apache.commons.cli.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.nio.file.Paths;

public class MetalpetLauncher {

	private static final String ROOT_PACKAGE = "org.pemacy.metalpet";
	private static final String DEFAULT_PROJECT_FILE = "metalpet.json";

	private static final Option FILE_OPTION = Option.builder("f")
		.argName("file")
		.optionalArg(true)
		.desc("Template project file.")
		.type(String.class)
		.build();

	private static final Options OPTIONS = new Options()
		.addOption(FILE_OPTION);

	public static void main(String[] args) throws ParseException {
		final var cli = new DefaultParser().parse(OPTIONS, args);
		final var context = createApplicationContext();
		new Metalpet(context, createConfig(cli));
	}

	private static ApplicationContext createApplicationContext() {
		final var context = new AnnotationConfigApplicationContext();
		context.scan(ROOT_PACKAGE);
		context.refresh();
		return context;
	}

	private static MetalpetArguments createConfig(CommandLine cli) {
		return ImmutableMetalpetArguments.builder()
			.projectFilePath(Paths.get(cli.getArgList().isEmpty() ? DEFAULT_PROJECT_FILE : cli.getArgList().get(0)))
			.build();
	}

}
