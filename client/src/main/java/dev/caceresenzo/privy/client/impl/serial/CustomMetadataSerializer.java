package dev.caceresenzo.privy.client.impl.serial;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import dev.caceresenzo.privy.model.CustomMetadata;

@SuppressWarnings("serial")
public class CustomMetadataSerializer extends StdSerializer<CustomMetadata> {

	public CustomMetadataSerializer() {
		super(CustomMetadata.class);
	}

	@Override
	public void serialize(CustomMetadata value, JsonGenerator generator, SerializerProvider provider) throws IOException {
		if (value == null) {
			generator.writeNull();
		} else {
			generator.writeObject(value.toValues());
		}
	}

}