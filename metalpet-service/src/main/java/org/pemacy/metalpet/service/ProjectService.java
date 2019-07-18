package org.pemacy.metalpet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.pemacy.metalpet.model.Project;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class ProjectService {

	private final ObjectMapper objectMapper;

	public ProjectService(ObjectMapper objectMapper) {
		this.objectMapper = checkNotNull(objectMapper);
	}

	public void execute(String fileName) throws IOException {
		checkNotNull(fileName);
		final var path = Paths.get(fileName).normalize().toAbsolutePath();
		checkArgument(Files.exists(path), "file does not exist: " + path);
		execute(Files.newInputStream(path));
	}

	public void execute(InputStream inputStream) throws IOException {
		checkNotNull(inputStream, "input stream cannot be null");
		final var project = parseProjectModel(inputStream);
		System.out.println(project); // TODO
	}

	public Project parseProjectModel(InputStream inputStream) throws IOException {
		checkNotNull(inputStream, "input stream cannot be null");
		return objectMapper.readValue(inputStream, Project.class);
	}

}
