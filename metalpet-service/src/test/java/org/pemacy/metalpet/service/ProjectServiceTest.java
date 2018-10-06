package org.pemacy.metalpet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.pemacy.metalpet.json.MetalpetModule;
import org.pemacy.metalpet.model.Project;
import org.pemacy.metalpet.model.file.target.FileTargetIgnore;
import org.pemacy.metalpet.model.file.target.matcher.MatcherFileTarget;
import org.pemacy.metalpet.model.input.StandardInputType;
import org.pemacy.metalpet.model.input.UserInput;
import org.pemacy.metalpet.model.operation.DeleteFilesOperation;
import org.pemacy.metalpet.model.operation.FileNameSearchAndModifyOperation;
import org.pemacy.metalpet.model.string.ReplaceStringModification;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author Peter André Johansen
 */
public class ProjectServiceTest {

	private static ProjectService projectService;

	@BeforeAll
	public static void createProjectService() {
		final var objectMapper = new ObjectMapper();
		objectMapper.registerModules(
			new GuavaModule(),
			new MetalpetModule()
		);
		projectService = new ProjectService(objectMapper);
	}

	@Test
	public void parseComplexProjectModel() throws IOException {
		final var jsonString =
		"{" +
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
		try (final var jsonInputStream = new ByteArrayInputStream(jsonString.getBytes(UTF_8.displayName()))) {
			final var parsedProject = projectService.parseProjectModel(jsonInputStream);
			final var expectedProject = new Project(
				"foobar",
				Collections.singletonList(
					new UserInput(
						"Enter desired Maven module directory prefix",
						StandardInputType.STRING,
						"maven-module-directory-prefix",
						true,
						null
					)
				),
				Arrays.asList(
					new DeleteFilesOperation(
						"Deleting .git/ directory...",
						Collections.singletonList(new MatcherFileTarget(
							".git/", null
						))
					),
					new FileNameSearchAndModifyOperation(
						"Renaming module directories...",
						Collections.singletonList(
							new MatcherFileTarget(
								"java-rest-skeleton-*",
								FileTargetIgnore.FILES
							)
						),
						Collections.singletonList(
							new ReplaceStringModification(
								"java-rest-skeleton",
								"${maven-module-directory-prefix}"
							)
						)
					)
				)
			);
			assertThat(parsedProject, is(equalTo(expectedProject)));
		}
	}

	@Test
	public void parseSimpleProjectModel() throws IOException {
		final var jsonString = "{ \"name\": \"foobar\", \"input\": [], \"operations\": [] }";
		try (final var jsonInputStream = new ByteArrayInputStream(jsonString.getBytes(UTF_8.displayName()))) {
			final var parsedProject = projectService.parseProjectModel(jsonInputStream);
			final var expectedProject = new Project("foobar", Collections.emptyList(), Collections.emptyList());
			assertThat(parsedProject, is(equalTo(expectedProject)));
		}
	}

}