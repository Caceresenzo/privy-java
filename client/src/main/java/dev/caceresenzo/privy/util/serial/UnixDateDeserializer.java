package dev.caceresenzo.privy.util.serial;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

@SuppressWarnings("serial")
public class UnixDateDeserializer extends StdDeserializer<Date> {

	public UnixDateDeserializer() {
		super(Date.class);
	}

	@Override
	public Date deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		final var timestamp = parser.getLongValue();

		return fromTimestamp(timestamp);
	}

	public static Date fromTimestamp(long timestamp) {
		return new Date(timestamp * 1000);
	}

}