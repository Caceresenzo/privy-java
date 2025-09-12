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

	private static final CustomMetadata EMPTY = new CustomMetadata(Collections.emptyMap());

	private final Map<String, Object> storage;

	public CustomMetadata() {
		this(new HashMap<>());
	}

	private CustomMetadata(Map<String, Object> storage) {
		this.storage = storage;
	}

	/**
	 * Get a stored string.
	 * 
	 * @param key The metadata's key.
	 * @return The value or empty if not found or type mismatch.
	 */
	public Optional<String> getString(String key) {
		if (storage.get(key) instanceof String value) {
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
			storage.put(key, value);
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
		final var rawValue = storage.get(key);

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
		storage.put(key, value);

		return this;
	}

	/**
	 * Get a stored decimal. As long as the value is a number, the {@link Number#doubleValue() double value} is returned.
	 * 
	 * @param key The metadata's key.
	 * @return The value or empty if not found or type mismatch.
	 */
	public Optional<Double> getDecimal(String key) {
		final var rawValue = storage.get(key);

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
		storage.put(key, value);

		return this;
	}

	/**
	 * Get a stored boolean.
	 * 
	 * @param key The metadata's key.
	 * @return The value or empty if not found or type mismatch.
	 */
	public Optional<Boolean> getBoolean(String key) {
		final var rawValue = storage.get(key);

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
		storage.put(key, value);

		return this;
	}

	/**
	 * Remove a value from the metadata.
	 *  
	 * @param key The metadata's key to remove.
	 * @return The value associated with the key, or <code>null</code> if not found.
	 */
	public Object remove(String key) {
		return storage.remove(key);
	}

	/**
	 * Get the metadata's size.
	 * 
	 * @return The number of storage stored in this metadata.
	 */
	public int size() {
		return storage.size();
	}

	@Override
	public String toString() {
		return "CustomMetadata(%s)".formatted(storage);
	}

	/**
	 * Get the storage of this metadata as a map.
	 * 
	 * @return A map containing the metadata's storage.
	 */
	public Map<String, Object> toMap() {
		return new HashMap<>(storage);
	}

	/**
	 * Create a new {@link CustomMetadata} from the given storage. <br />
	 * The storage are filtered to only include {@link String}, {@link Number}, and {@link Boolean} types.
	 * 
	 * @param storage The storage to create the metadata from.
	 * @return A new {@link CustomMetadata} instance containing the filtered storage.
	 */
	public static CustomMetadata fromMap(Map<String, ?> values) {
		final var filteredValues = new HashMap<String, Object>(values.size());

		for (final var entry : values.entrySet()) {
			final var value = entry.getValue();

			if (value instanceof String || value instanceof Number || value instanceof Boolean) {
				filteredValues.put(entry.getKey(), value);
			}
		}

		return new CustomMetadata(filteredValues);
	}

	public static CustomMetadata empty() {
		return EMPTY;
	}

}