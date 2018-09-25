package org.pemacy.metalpet.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableSet;

import java.io.IOException;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Peter André Johansen
 */
public class PropertyMatchingDeserializer<T> extends StdDeserializer<T> {

	private final ImmutableSet<Class<? extends T>> subtypes;

	public PropertyMatchingDeserializer(Class<T> type, Set<Class<? extends T>> subtypes) {
		super(checkNotNull(type));
		checkNotNull(subtypes);
		checkArgument(!subtypes.isEmpty(), "set of subtypes cannot be empty");
		this.subtypes = ImmutableSet.copyOf(subtypes);
	}

	@Override
	public T deserialize(JsonParser parser, DeserializationContext context) throws IOException {
		final var mapper = (ObjectMapper) parser.getCodec();
		final var node = (ObjectNode) mapper.readTree(parser);
		final var fieldNames = ImmutableSet.copyOf(node.fieldNames());
		final var type =
			subtypes.stream()
					.filter(t -> isPropertiesMatch(t, fieldNames))
					.findFirst()
					.orElseThrow(() -> new JsonMappingException(parser, "no types with matching properties found"));
		return mapper.readValue(node.toString(), type);
	}

	public Set<Class<? extends T>> getSubtypes() {
		return subtypes;
	}

	private boolean isPropertiesMatch(Class<? extends T> type, Set<String> propertyNames) {
		var match = true;
		for (final var field : type.getDeclaredFields()) {
			for (final var annotation : field.getAnnotationsByType(JsonProperty.class)) {
				final var name = annotation.value().equals(JsonProperty.USE_DEFAULT_NAME)
									? field.getName() : annotation.value();
				if (!propertyNames.contains(name)) {
					match = false;
				}
			}
			if (!match) {
				break;
			}
		}
		return match;
	}

}
