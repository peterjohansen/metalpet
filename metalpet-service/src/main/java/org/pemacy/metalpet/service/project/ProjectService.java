package org.pemacy.metalpet.service.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.pemacy.metalpet.model.input.StandardInputType;
import org.pemacy.metalpet.model.input.UserInput;
import org.pemacy.metalpet.model.project.*;
import org.pemacy.metalpet.service.input.InputService;
import org.pemacy.metalpet.service.output.OutputService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class ProjectService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectService.class);

	private final InputService inputService;
	private final OutputService outputService;
	private final ObjectMapper objectMapper;

	public ProjectService(InputService inputService, OutputService outputService, ObjectMapper objectMapper) {
		this.inputService = checkNotNull(inputService);
		this.outputService = checkNotNull(outputService);
		this.objectMapper = checkNotNull(objectMapper);

		LOGGER.info("Project service initialized.");
	}

	public OngoingProject execute(OngoingProject ongoingProject) {
		checkNotNull(ongoingProject);

		LOGGER.info("Executing project: {}", ongoingProject);
		ongoingProject = outputLoadedProjectInfo(ongoingProject);
		ongoingProject = retrieveVariablesFromUserInputs(ongoingProject);
		ongoingProject = performOperations(ongoingProject);
		ongoingProject = outputFinishedProjectInfo(ongoingProject);
		LOGGER.info("Project successfully executed: {}", ongoingProject);

		return ongoingProject;
	}

	public Project parseProject(String fileName) throws IOException {
		checkNotNull(fileName);
		final var path = Paths.get(fileName).normalize().toAbsolutePath();
		checkArgument(Files.exists(path), "file does not exist: " + path);
		return parseProject(Files.newInputStream(path), path);
	}

	public Project parseProject(InputStream inputStream) throws IOException {
		checkNotNull(inputStream);
		return parseProject(inputStream, null);
	}

	public OngoingProject setProjectExecutionListener(OngoingProject ongoingProject, ProjectExecutionListener listener) {
		checkNotNull(ongoingProject);
		checkNotNull(listener);
		return ImmutableOngoingProject.copyOf(ongoingProject).withExecutionListener(Optional.of(listener));
	}

	public OngoingProject startProject(Project projectModel) {
		checkNotNull(projectModel);
		return ImmutableOngoingProject.of(projectModel);
	}

	private String buildUserInputPrompt(UserInput userInput) {
		final var prompt = new StringBuilder();
		prompt.append(userInput.getPrompt());
		if (userInput.getType() == StandardInputType.BOOLEAN) {
			prompt.append(" (Y/n)");
		}
		userInput.getDefaultValue().ifPresent(defaultValue -> {
			prompt.append("(default ");
			prompt.append(defaultValue);
			prompt.append(")");
		});
		prompt.append(": ");
		return prompt.toString();
	}

	private OngoingProject outputLoadedProjectInfo(OngoingProject ongoingProject) {
		ongoingProject = setStep(ongoingProject, ExecutionStep.DISPLAYING_PROJECT_INFO);
		final var projectModel = ongoingProject.getProjectModel();
		projectModel.getProjectFile().ifPresent(path -> outputService.printfln("Project file path: %s", path));
		outputService.printfln("Project loaded: %s", projectModel.getName());

		final var inputs = projectModel.getUserInputList();
		if (inputs.isEmpty()) {
			outputService.printfln("Project requires no input.");
		} else {
			outputService.printfln("Project requires %s %s.", inputs.size(), inputs.size() == 1 ? "input": "inputs");
		}
		outputService.println();
		return ongoingProject;
	}

	private OngoingProject outputFinishedProjectInfo(OngoingProject ongoingProject) {
		outputService.println("Project successfully executed!");
		ongoingProject = setStep(ongoingProject, ExecutionStep.FINISHED);
		return ongoingProject;
	}

	private Project parseProject(InputStream inputStream, Path projectFilePath) throws IOException {
		checkNotNull(inputStream, "input stream cannot be null");
		return ImmutableProject.copyOf(objectMapper.readValue(inputStream, Project.class))
			.withProjectFile(Optional.ofNullable(projectFilePath));
	}

	private OngoingProject performOperations(OngoingProject ongoingProject) {
		final var projectModel = ongoingProject.getProjectModel();
		if (projectModel.getOperations().isEmpty()) {
			outputService.println("No operations to perform!");
		} else {
			for (final var operation : projectModel.getOperations()) {
				LOGGER.info("Performing operation: {}", operation.getIdentifier());
				ongoingProject = setStep(ongoingProject, ExecutionStep.PERFORMING_OPERATION);
				outputService.printfln("%s...", operation.getReport());



				ongoingProject = setStep(ongoingProject, ExecutionStep.OPERATION_COMPLETE);
				LOGGER.info("Operation complete: {}", operation.getIdentifier());
			}
		}
		outputService.println();
		return ongoingProject;
	}

	private OngoingProject retrieveVariablesFromUserInputs(OngoingProject ongoingProject) {
		final var variables = new HashMap<String, Object>();
		for (final var userInput : ongoingProject.getProjectModel().getUserInputList()) {
			LOGGER.info("Asking user for input: {}", userInput);
			ongoingProject = setStep(ongoingProject, ExecutionStep.ASKING_FOR_INPUT);
			outputService.print(buildUserInputPrompt(userInput));

			ongoingProject = setStep(ongoingProject, ExecutionStep.AWAITING_INPUT);
			final var inputValue = inputService.valueOrDefault(userInput, inputService.nextUserInput());

			ongoingProject = setStep(ongoingProject, ExecutionStep.PROCESSING_INPUT);
			final var parsedInputValue = inputService.parseInputvalue(userInput.getType(), inputValue);
			variables.put(userInput.getVariable(), parsedInputValue);
			ongoingProject = ImmutableOngoingProject.copyOf(ongoingProject).withVariables(variables);
			LOGGER.info("Variable created from user input: {} = {}", userInput.getVariable(), parsedInputValue);
		}
		outputService.println();
		return ongoingProject;
	}

	private OngoingProject setStep(OngoingProject ongoingProject, ExecutionStep step) {
		assert step != ongoingProject.getCurrentExecutionStep();
		LOGGER.info("Project execution step changed from {} to {}.", ongoingProject.getCurrentExecutionStep(), step);
		ongoingProject.getExecutionListener().ifPresent(listener -> listener.onProjectExecutionStepChange(step));
		return ImmutableOngoingProject.copyOf(ongoingProject).withCurrentExecutionStep(step);
	}

}
