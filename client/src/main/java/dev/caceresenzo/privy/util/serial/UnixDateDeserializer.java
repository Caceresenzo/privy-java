package dev.caceresenzo.privy.util.serial;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

@SuppressWarnings("serial")
public abstract class UnixDateDeserializer extends StdDeserializer<Date> {

	private final long millisecondsPerUnit;

	protected UnixDateDeserializer(long millisecondsPerUnit) {
		super(Date.class);

		this.millisecondsPerUnit = millisecondsPerUnit;
	}

	@Override
	public Date deserialize(JsonParser parser, DeserializationContext context) throws IOException {
		return from(parser.getLongValue(), millisecondsPerUnit);
	}

	public static Date from(long timestamp, long millisecondsPerUnit) {
		return new Date(Math.multiplyExact(timestamp, millisecondsPerUnit));
	}

	public static final class AsSeconds extends UnixDateDeserializer {

		public static final long SECONDS_PER_UNIT = 1000L;

		public AsSeconds() {
			super(SECONDS_PER_UNIT);
		}

		public static Date from(long timestamp) {
			return from(timestamp, SECONDS_PER_UNIT);
		}

	}

	public static final class AsMilliseconds extends UnixDateDeserializer {

		public static final long MILLISECONDS_PER_UNIT = 1L;

		public AsMilliseconds() {
			super(1L);
		}

		public static Date from(long timestamp) {
			return from(timestamp, MILLISECONDS_PER_UNIT);
		}

	}

}