package dev.caceresenzo.privy.util;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import dev.caceresenzo.privy.util.serial.UnixDateDeserializer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PrivyUtils {

	public static ObjectMapper createMapper() {
		final var module = new SimpleModule();
		module.addDeserializer(Date.class, new UnixDateDeserializer());

		return JsonMapper.builder()
			.serializationInclusion(JsonInclude.Include.NON_NULL)
			.configure(SerializationFeature.INDENT_OUTPUT, true)
			.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
			.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.addModule(module)
			.build();
	}

}