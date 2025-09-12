package dev.caceresenzo.privy.client.impl.serial;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import dev.caceresenzo.privy.model.CustomMetadata;

@SuppressWarnings("serial")
public class CustomMetadataDeserializer extends StdDeserializer<CustomMetadata> {

	public static final TypeReference<Map<String, Object>> STORAGE_MAP = new TypeReference<>() {};

	public CustomMetadataDeserializer() {
		super(CustomMetadata.class);
	}

	@Override
	public CustomMetadata deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
		return CustomMetadata.fromMap(parser.readValueAs(STORAGE_MAP));
	}

}