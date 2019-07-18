package org.pemacy.metalpet.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;

public class TypeSpecificationDeserializer<T> extends StdDeserializer<T> {

	@FunctionalInterface
	public interface TypeMapper<T> extends Function<String, Class<? extends T>> {
	}

	private final String typePropertyName;
	private final TypeMapper<T> typeMapper;

	public TypeSpecificationDeserializer(Class<T> type, String typePropertyName, TypeMapper<T> typeMapper) {
		super(checkNotNull(type));
		this.typePropertyName = checkNotNull(typePropertyName);
		this.typeMapper = checkNotNull(typeMapper);
	}

	@Override
	public T deserialize(JsonParser parser, DeserializationContext context) throws IOException {
		final var mapper = (ObjectMapper) parser.getCodec();
		final var node = (ObjectNode) mapper.readTree(parser);
		final var property = node.get(typePropertyName);
		if (property == null || property.isNull()) {
			throw new JsonMappingException(parser, "type specification property '" + typePropertyName + "' is not a value");
		}
		if (!property.isTextual()) {
			throw new JsonMappingException(parser, "type specification property '" + typePropertyName + "' is not a string");
		}
		final var typeID = property.asText();
		final var type = typeMapper.apply(typeID);
		return mapper.readValue(node.toString(), type);
	}

	public TypeMapper<T> getTypeMapper() {
		return typeMapper;
	}

	public String getTypePropertyName() {
		return typePropertyName;
	}

}
