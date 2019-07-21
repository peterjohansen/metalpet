package org.pemacy.metalpet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.pemacy.metalpet.json.MetalpetModule;
import org.pemacy.metalpet.model.ImmutableProject;
import org.pemacy.metalpet.model.Project;
import org.pemacy.metalpet.model.file.FileTargetIgnore;
import org.pemacy.metalpet.model.file.ImmutableDeleteFilesOperation;
import org.pemacy.metalpet.model.file.ImmutableFileNameSearchAndModifyOperation;
import org.pemacy.metalpet.model.file.ImmutableMatcherFileTarget;
import org.pemacy.metalpet.model.input.ImmutableUserInput;
import org.pemacy.metalpet.model.input.StandardInputType;
import org.pemacy.metalpet.model.string.ImmutableReplaceStringModification;
import org.pemacy.metalpet.output.OutputService;
import org.pemacy.metalpet.service.input.InputService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

public class ProjectServiceTest {

	private static ProjectService projectService;

	@BeforeAll
	public static void createProjectService() {
		final var objectMapper = new ObjectMapper();
		objectMapper.registerModules(
			new GuavaModule(),
			new MetalpetModule()
		);
		projectService = new ProjectService(mock(InputService.class), mock(OutputService.class), objectMapper);
	}

	@Test
	public void parseComplexProjectModel() throws IOException {
		final var jsonString = createComplexProjectModelAsJson();
		try (final var jsonInputStream = new ByteArrayInputStream(jsonString.getBytes(UTF_8.displayName()))) {
			final var parsedProject = projectService.parseProject(jsonInputStream);
			final var expectedProject = createComplexProjectModelAsObjects();
			assertThat(parsedProject, is(equalTo(expectedProject)));
		}
	}

	@Test
	public void parseSimpleProjectModel() throws IOException {
		final var jsonString = "{ \"name\": \"foobar\", \"input\": [], \"operations\": [] }";
		try (final var jsonInputStream = new ByteArrayInputStream(jsonString.getBytes(UTF_8.displayName()))) {
			final var parsedProject = projectService.parseProject(jsonInputStream);
			final var expectedProject = ImmutableProject.builder().name("foobar").build();
			assertThat(parsedProject, is(equalTo(expectedProject)));
		}
	}

	/**
	 * Is the JSON equivalent of {@link #createComplexProjectModelAsObjects()}.
	 */
	private String createComplexProjectModelAsJson() {
		return "{" +
				"\"name\": \"foobar\"," +
				"\"input\": [" +
					"{" +
						"\"prompt\": \"Enter desired Maven module directory prefix\"," +
						"\"type\": \"string\"," +
						"\"variable\": \"maven-module-directory-prefix\"," +
						"\"optional\": true," +
						"\"defaultValue\": null" +
					"}" +
				"]," +
				"\"operations\": [" +
					"{" +
						"\"type\": \"DELETE_FILES\"," +
						"\"report\": \"Deleting .git/ directory...\"," +
						"\"targets\": [" +
							"{" +
								"\"glob\": \".git/\"" +
							"}" +
						"]" +
					"}," +
					"{" +
						"\"type\": \"FILE_NAME_SEARCH_AND_MODIFY\"," +
						"\"report\": \"Renaming module directories...\"," +
						"\"targets\": [" +
							"{" +
								"\"glob\": \"java-rest-skeleton-*\"," +
								"\"ignore\": \"files\"" +
							"}" +
						"]," +
						"\"modifications\": [" +
							"{" +
								"\"replace\": \"java-rest-skeleton\"," +
								"\"with\": \"${maven-module-directory-prefix}\"" +
							"}" +
						"]" +
					"}" +
				"]" +
			"}";
	}

	/**
	 * Is the objects equivalent of {@link #createComplexProjectModelAsJson()}.
	 */
	private Project createComplexProjectModelAsObjects() {
		final var project = ImmutableProject.builder();
		project.name("foobar");
		project.addUserInputList(
			ImmutableUserInput.builder()
				.prompt("Enter desired Maven module directory prefix")
				.type(StandardInputType.STRING)
				.variable("maven-module-directory-prefix")
				.defaultValue(Optional.empty())
				.build()
		);
		project.addOperations(
			ImmutableDeleteFilesOperation.builder()
				.report("Deleting .git/ directory...")
				.addTargets(ImmutableMatcherFileTarget.builder().glob(".git/").build())
				.build()
		);
		project.addOperations(
			ImmutableFileNameSearchAndModifyOperation.builder()
				.report("Renaming module directories...")
				.addTargets(ImmutableMatcherFileTarget.builder()
					.glob("java-rest-skeleton-*")
					.ignore(FileTargetIgnore.FILES)
					.build()
				)
				.addModifications(ImmutableReplaceStringModification.builder()
					.target("java-rest-skeleton")
					.replacement("${maven-module-directory-prefix}")
					.build())
				.build()
		);
		return project.build();
	}

}
