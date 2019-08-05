package org.pemacy.metalpet.service.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.pemacy.metalpet.model.input.StandardInputType;
import org.pemacy.metalpet.model.input.UserInput;
import org.pemacy.metalpet.model.project.*;
import org.pemacy.metalpet.service.input.InputService;
import org.pemacy.metalpet.service.operation.OperationService;
import org.pemacy.metalpet.service.output.OutputService;
import org.pemacy.metalpet.service.project.exception.ProjectLoadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class ProjectService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectService.class);

	private final InputService inputService;
	private final OutputService outputService;
	private final OperationService operationService;
	private final ObjectMapper objectMapper;

	@Autowired
	public ProjectService(InputService inputService,
						  OutputService outputService,
						  OperationService operationService,
						  ObjectMapper objectMapper) {
		this.inputService = checkNotNull(inputService);
		this.outputService = checkNotNull(outputService);
		this.operationService = checkNotNull(operationService);
		this.objectMapper = checkNotNull(objectMapper);

		LOGGER.info("Project service initialized.");
	}

	public OngoingProject execute(OngoingProject ongoingProject) {
		checkNotNull(ongoingProject);
		checkArgument(ongoingProject.getCurrentExecutionStep() == ExecutionStep.READY_TO_EXECUTE,
			"project is not in initial execution step");

		LOGGER.info("Executing project: {}", ongoingProject);
		ongoingProject = outputLoadedProjectInfo(ongoingProject);
		ongoingProject = retrieveVariablesFromUserInputs(ongoingProject);
		ongoingProject = performOperations(ongoingProject);
		ongoingProject = outputFinishedProjectInfo(ongoingProject);
		LOGGER.info("Project successfully executed: {}", ongoingProject);

		return ongoingProject;
	}

	public Project parseProject(Path file) {
		checkNotNull(file);
		checkArgument(Files.exists(file), "file does not exist: " + file);
		try {
			return parseProject(Files.newInputStream(file), file);
		} catch (IOException e) {
			throw new ProjectLoadException(e);
		}
	}

	public Project parseProject(InputStream inputStream) {
		checkNotNull(inputStream);
		try {
			return parseProject(inputStream, null);
		} catch (IOException e) {
			throw new ProjectLoadException(e);
		}
	}

	public OngoingProject setProjectExecutionListener(OngoingProject ongoingProject, ProjectExecutionListener listener) {
		checkNotNull(ongoingProject);
		checkNotNull(listener);
		return ImmutableOngoingProject.copyOf(ongoingProject).withExecutionListener(Optional.of(listener));
	}

	public OngoingProject startProject(Project projectModel) {
		checkNotNull(projectModel);
		final var project = ImmutableOngoingProject.of(projectModel);
		setStep(project, project.getCurrentExecutionStep());
		return project;
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

		LOGGER.info("Project information displayed.");

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
			final var rootDirectory = ongoingProject.getProjectModel().getProjectFile()
				.map(Path::getParent).orElseGet(() -> Path.of("."));
			for (final var operation : projectModel.getOperations()) {
				ongoingProject = setStep(ongoingProject, ExecutionStep.PERFORMING_OPERATION);
				LOGGER.info("Performing operation: {}", operation.getIdentifier());

				operationService.perform(rootDirectory, operation);

				ongoingProject = setStep(ongoingProject, ExecutionStep.OPERATION_COMPLETE);
				LOGGER.info("Operation complete: {}", operation.getIdentifier());
			}
		}
		outputService.println();
		return ongoingProject;
	}

	private OngoingProject retrieveVariablesFromUserInputs(OngoingProject ongoingProject) {
		final var userInputList = ongoingProject.getProjectModel().getUserInputList();
		final var variables = new HashMap<String, Object>();
		for (final var userInput : userInputList) {
			ongoingProject = setStep(ongoingProject, ExecutionStep.ASKING_FOR_INPUT);
			LOGGER.info("Asking user for input: {}", userInput);
			outputService.print(buildUserInputPrompt(userInput));

			ongoingProject = setStep(ongoingProject, ExecutionStep.AWAITING_INPUT);
			LOGGER.info("Waiting for input from user...");
			final var inputValue = inputService.valueOrDefault(userInput, inputService.nextUserInput());

			ongoingProject = setStep(ongoingProject, ExecutionStep.PROCESSING_INPUT);
			final var parsedInputValue = inputService.parseInputValue(userInput.getType(), inputValue);
			variables.put(userInput.getVariable(), parsedInputValue);
			ongoingProject = ImmutableOngoingProject.copyOf(ongoingProject).withVariables(variables);
			LOGGER.info("Variable created from user input: {} = {}", userInput.getVariable(), parsedInputValue);
		}
		if (!userInputList.isEmpty()) {
			outputService.println();
		}
		return ongoingProject;
	}

	private OngoingProject setStep(OngoingProject ongoingProject, ExecutionStep step) {
		if (step == ExecutionStep.READY_TO_EXECUTE) {
			LOGGER.info("Project execution step changed to {}.", step);
		} else {
			assert step != ongoingProject.getCurrentExecutionStep();
			LOGGER.info("Project execution step changed from {} to {}.", ongoingProject.getCurrentExecutionStep(), step);
		}
		ongoingProject.getExecutionListener().ifPresent(listener -> listener.onProjectExecutionStepChange(step));
		return ImmutableOngoingProject.copyOf(ongoingProject).withCurrentExecutionStep(step);
	}

}
