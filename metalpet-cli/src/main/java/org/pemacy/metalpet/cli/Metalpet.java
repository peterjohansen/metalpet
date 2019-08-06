package org.pemacy.metalpet.cli;

import org.pemacy.metalpet.model.project.ExecutionStep;
import org.pemacy.metalpet.service.project.ProjectService;
import org.springframework.context.ApplicationContext;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

import static com.google.common.base.Preconditions.checkNotNull;

public class Metalpet {

	public Metalpet(ApplicationContext context, MetalpetArguments config) {
		checkNotNull(context);
		checkNotNull(config);

		disableIllegalAccessWarning(); // TODO Temporary to disable an annoying warning

		final var projectService = context.getBean(ProjectService.class);
		final var projectModel = projectService.parseProject(config.getProjectFilePath());
		final var ongoingProject = projectService.startProject(projectModel);
		final var finishedProject = projectService.execute(ongoingProject);
		assert (finishedProject.getCurrentExecutionStep() == ExecutionStep.FINISHED);
	}

	private static void disableIllegalAccessWarning() {
		try {
			Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
			theUnsafe.setAccessible(true);
			Unsafe u = (Unsafe) theUnsafe.get(null);

			Class cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
			Field logger = cls.getDeclaredField("logger");
			u.putObjectVolatile(cls, u.staticFieldOffset(logger), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
