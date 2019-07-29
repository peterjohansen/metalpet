package org.pemacy.metalpet.cli;

import org.pemacy.metalpet.model.project.ExecutionStep;
import org.pemacy.metalpet.service.project.ProjectService;
import org.springframework.context.ApplicationContext;

import java.util.Objects;

public class Metalpet {

	public Metalpet(ApplicationContext context, MetalpetArguments config) {
		Objects.requireNonNull(context);
		Objects.requireNonNull(config);

		final var projectService = context.getBean(ProjectService.class);
		final var projectModel = projectService.parseProject(config.getProjectFilePath());
		final var ongoingProject = projectService.startProject(projectModel);
		final var finishedProject = projectService.execute(ongoingProject);
		assert (finishedProject.getCurrentExecutionStep() == ExecutionStep.FINISHED);
	}

}
