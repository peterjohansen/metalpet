package org.pemacy.metalpet.json.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Test;
import org.pemacy.metalpet.json.PropertyMatchingDeserializer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author Peter André Johansen
 */
public class PropertyMatchingDeserializerTest {

	private static class ClassSupertype {}
	private static class ClassSubtype1 extends ClassSupertype {
		@JsonProperty private String a;
		@JsonProperty private int b;
	}
	private static class ClassSubtype2 extends ClassSupertype {
		@JsonProperty private boolean c;
	}
	@Test
	public void correctClassSubtypeChosen() throws Exception {
		final var objectMapper = new ObjectMapper();
		final var module = new SimpleModule();
		module.addDeserializer(ClassSupertype.class, new PropertyMatchingDeserializer<>(
			ClassSupertype.class, ImmutableSet.of(ClassSubtype1.class, ClassSubtype2.class)
		));
		objectMapper.registerModule(module);

		var object1 = objectMapper.readValue("{ \"a\": \"foobar\", \"b\": 7 }", ClassSupertype.class);
		assertThat(object1.getClass(), is(equalTo(ClassSubtype1.class)));
		assertThat(((ClassSubtype1) object1).a, is(equalTo("foobar")));
		assertThat(((ClassSubtype1) object1).b, is(equalTo(7)));

		var object2 = objectMapper.readValue("{ \"c\": true }", ClassSupertype.class);
		assertThat(object2.getClass(), is(equalTo(ClassSubtype2.class)));
		assertThat(((ClassSubtype2) object2).c, is(equalTo(true)));
	}

	private interface InterfaceSupertype {}
	private static class InterfaceSubtype1 implements InterfaceSupertype {
		@JsonProperty private String a;
		@JsonProperty private int b;
	}
	private static class InterfaceSubtype2 implements InterfaceSupertype {
		@JsonProperty private boolean c;
	}
	@Test
	public void correctInterfaceSubtypeChosen() throws Exception {
		final var objectMapper = new ObjectMapper();
		final var module = new SimpleModule();
		module.addDeserializer(InterfaceSupertype.class, new PropertyMatchingDeserializer<>(
			InterfaceSupertype.class, ImmutableSet.of(InterfaceSubtype1.class, InterfaceSubtype2.class)
		));
		objectMapper.registerModule(module);

		final var object1 = objectMapper.readValue("{ \"a\": \"foobar\", \"b\": 7 }", InterfaceSupertype.class);
		assertThat(object1.getClass(), is(equalTo(InterfaceSubtype1.class)));
		assertThat(((InterfaceSubtype1) object1).a, is(equalTo("foobar")));
		assertThat(((InterfaceSubtype1) object1).b, is(equalTo(7)));

		final var object2 = objectMapper.readValue("{ \"c\": true }", InterfaceSupertype.class);
		assertThat(object2.getClass(), is(equalTo(InterfaceSubtype2.class)));
		assertThat(((InterfaceSubtype2) object2).c, is(equalTo(true)));
	}

	private interface JsonAnnotationNameRespectedSupertype {}
	private static class JsonAnnotationNameRespectedSubtype implements JsonAnnotationNameRespectedSupertype {
		@JsonProperty("b") private String a;
	}
	@Test
	public void jsonAnnotationNameRespected() throws Exception{
		final var objectMapper = new ObjectMapper();
		final var module = new SimpleModule();
		module.addDeserializer(JsonAnnotationNameRespectedSupertype.class, new PropertyMatchingDeserializer<>(
			JsonAnnotationNameRespectedSupertype.class, ImmutableSet.of(JsonAnnotationNameRespectedSubtype.class)
		));
		objectMapper.registerModule(module);

		final var object = objectMapper.readValue("{ \"b\": \"foobar\" }", JsonAnnotationNameRespectedSupertype.class);
		assertThat(object.getClass(), is(equalTo(JsonAnnotationNameRespectedSubtype.class)));
		assertThat(((JsonAnnotationNameRespectedSubtype) object).a, is(equalTo("foobar")));
	}

}
