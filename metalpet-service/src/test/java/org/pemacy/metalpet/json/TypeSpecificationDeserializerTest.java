package org.pemacy.metalpet.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TypeSpecificationDeserializerTest {

	@JsonIgnoreProperties(ignoreUnknown = true) private interface InterfaceSupertype {}
	@JsonIgnoreProperties(ignoreUnknown = true) private static class InterfaceSubtype1 implements InterfaceSupertype {}
	@JsonIgnoreProperties(ignoreUnknown = true) private static class InterfaceSubtype2 implements InterfaceSupertype {}

	@JsonIgnoreProperties(ignoreUnknown = true) private static class ClassSupertype {}
	@JsonIgnoreProperties(ignoreUnknown = true) private static class ClassSubtype1 extends ClassSupertype {}
	@JsonIgnoreProperties(ignoreUnknown = true) private static class ClassSubtype2 extends ClassSupertype {}

	@Test
	public void correctClassSubtypeChosen() throws Exception {
		final var objectMapper = new ObjectMapper();
		final var module = new SimpleModule();
		module.addDeserializer(ClassSupertype.class, new TypeSpecificationDeserializer<>(
			ClassSupertype.class, "t", id -> {
			if (id.equals("a")) { return ClassSubtype1.class; }
			if (id.equals("b")) { return ClassSubtype2.class; }
			throw new AssertionError();
		}
		));
		objectMapper.registerModule(module);
		final var jsonFormat = "{ \"t\": \"%s\" }";
		final var object1 = objectMapper.readValue(String.format(jsonFormat, "a"), ClassSupertype.class);
		assertThat(object1.getClass(), is(equalTo(ClassSubtype1.class)));
		final var object2 = objectMapper.readValue(String.format(jsonFormat, "b"), ClassSupertype.class);
		assertThat(object2.getClass(), is(equalTo(ClassSubtype2.class)));

	}

	@Test
	public void correctInterfaceSubtypeChosen() throws Exception {
		final var objectMapper = new ObjectMapper();
		final var module = new SimpleModule();
		module.addDeserializer(InterfaceSupertype.class, new TypeSpecificationDeserializer<>(
			InterfaceSupertype.class, "t", id -> {
				if (id.equals("a")) { return InterfaceSubtype1.class; }
				if (id.equals("b")) { return InterfaceSubtype2.class; }
				throw new AssertionError();
			}
		));
		objectMapper.registerModule(module);
		final var jsonFormat = "{ \"t\": \"%s\" }";
		final var object1 = objectMapper.readValue(String.format(jsonFormat, "a"), InterfaceSupertype.class);
		assertThat(object1.getClass(), is(equalTo(InterfaceSubtype1.class)));
		final var object2 = objectMapper.readValue(String.format(jsonFormat, "b"), InterfaceSupertype.class);
		assertThat(object2.getClass(), is(equalTo(InterfaceSubtype2.class)));
	}

}
