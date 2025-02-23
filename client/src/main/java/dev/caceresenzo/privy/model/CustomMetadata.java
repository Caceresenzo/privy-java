package dev.caceresenzo.privy.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import dev.caceresenzo.privy.client.impl.serial.CustomMetadataDeserializer;
import dev.caceresenzo.privy.client.impl.serial.CustomMetadataSerializer;

@JsonSerialize(using = CustomMetadataSerializer.class)
@JsonDeserialize(using = CustomMetadataDeserializer.class)
public class CustomMetadata {

	private final Map<String, Object> values;

	public CustomMetadata() {
		this(new HashMap<>());
	}

	private CustomMetadata(Map<String, Object> values) {
		this.values = values;
	}

	/**
	 * Get a stored string.
	 * 
	 * @param key The metadata's key.
	 * @return The value or empty if not found or type mismatch.
	 */
	public Optional<String> getString(String key) {
		if (values.get(key) instanceof String value) {
			return Optional.of(value);
		}

		return Optional.empty();
	}

	/**
	 * Store a string.
	 * 
	 * @param key The metadata's key.
	 * @param value The metadata's value.
	 * @return <code>this</code>
	 */
	public CustomMetadata putString(String key, String value) {
		if (value == null) {
			remove(key);
		} else {
			values.put(key, value);
		}

		return this;
	}

	/**
	 * Get a stored number. As long as the value is a number, the {@link Number#longValue() long value} is returned.
	 * 
	 * @param key The metadata's key.
	 * @return The value or empty if not found or type mismatch.
	 */
	public Optional<Long> getNumber(String key) {
		final var rawValue = values.get(key);

		if (rawValue instanceof Number value) {
			return Optional.of(value.longValue());
		}

		return Optional.empty();
	}

	/**
	 * Store a number.
	 * 
	 * @param key The metadata's key.
	 * @param value The metadata's value.
	 * @return <code>this</code>
	 */
	public CustomMetadata putNumber(String key, long value) {
		values.put(key, value);

		return this;
	}

	/**
	 * Get a stored decimal. As long as the value is a number, the {@link Number#doubleValue() double value} is returned.
	 * 
	 * @param key The metadata's key.
	 * @return The value or empty if not found or type mismatch.
	 */
	public Optional<Double> getDecimal(String key) {
		final var rawValue = values.get(key);

		if (rawValue instanceof Number value) {
			return Optional.of(value.doubleValue());
		}

		return Optional.empty();
	}

	/**
	 * Store a decimal.
	 * 
	 * @param key The metadata's key.
	 * @param value The metadata's value.
	 * @return <code>this</code>
	 */
	public CustomMetadata putDecimal(String key, double value) {
		values.put(key, value);

		return this;
	}

	/**
	 * Get a stored boolean.
	 * 
	 * @param key The metadata's key.
	 * @return The value or empty if not found or type mismatch.
	 */
	public Optional<Boolean> getBoolean(String key) {
		final var rawValue = values.get(key);

		if (rawValue instanceof Boolean value) {
			return Optional.of(value);
		}

		return Optional.empty();
	}

	/**
	 * Store a boolean.
	 * 
	 * @param key The metadata's key.
	 * @param value The metadata's value.
	 * @return <code>this</code>
	 */
	public CustomMetadata putBoolean(String key, boolean value) {
		values.put(key, value);

		return this;
	}

	public Object remove(String key) {
		return values.remove(key);
	}

	@Override
	public String toString() {
		return "CustomMetadata(%s)".formatted(values);
	}

	public Map<String, Object> toValues() {
		return Collections.unmodifiableMap(values);
	}

	public static CustomMetadata fromValues(Map<String, Object> values) {
		return new CustomMetadata(new HashMap<>(values));
	}

}