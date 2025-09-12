package dev.caceresenzo.privy.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class CustomMetadataTest {

	CustomMetadata getMetadata() {
		return new CustomMetadata()
			.putString("string", "hello")
			.putNumber("number", 42)
			.putDecimal("decimal", 4.2)
			.putBoolean("boolean", true);
	}

	@Test
	void getString() {
		final var metadata = getMetadata();

		assertThat(metadata.getString("string"))
			.contains("hello");

		assertThat(metadata.getString("number"))
			.isEmpty();

		assertThat(metadata.getString("decimal"))
			.isEmpty();

		assertThat(metadata.getString("boolean"))
			.isEmpty();
	}

	@Test
	void putString() {
		final var metadata = getMetadata();

		metadata.putString("string", "world");

		assertThat(metadata.getString("string"))
			.contains("world");

		metadata.putString("string", null);

		assertThat(metadata.getString("string"))
			.isEmpty();
	}

	@Test
	void getNumber() {
		final var metadata = getMetadata();

		assertThat(metadata.getNumber("string"))
			.isEmpty();

		assertThat(metadata.getNumber("number"))
			.contains(42l);

		assertThat(metadata.getNumber("decimal"))
			.contains(4l);

		assertThat(metadata.getNumber("boolean"))
			.isEmpty();
	}

	@Test
	void putNumber() {
		final var metadata = getMetadata();

		metadata.putNumber("number", 43l);

		assertThat(metadata.getNumber("number"))
			.contains(43l);
	}

	@Test
	void getDecimal() {
		final var metadata = getMetadata();

		assertThat(metadata.getDecimal("string"))
			.isEmpty();

		assertThat(metadata.getDecimal("number"))
			.contains(42d);

		assertThat(metadata.getDecimal("decimal"))
			.contains(4.2d);

		assertThat(metadata.getDecimal("boolean"))
			.isEmpty();
	}

	@Test
	void putDecimal() {
		final var metadata = getMetadata();

		metadata.putDecimal("decimal", 4.3d);

		assertThat(metadata.getDecimal("decimal"))
			.contains(4.3d);
	}

	@Test
	void getBoolean() {
		final var metadata = getMetadata();

		assertThat(metadata.getBoolean("string"))
			.isEmpty();

		assertThat(metadata.getBoolean("number"))
			.isEmpty();

		assertThat(metadata.getBoolean("decimal"))
			.isEmpty();

		assertThat(metadata.getBoolean("boolean"))
			.contains(true);
	}

	@Test
	void putBoolean() {
		final var metadata = getMetadata();

		metadata.putBoolean("boolean", false);

		assertThat(metadata.getBoolean("boolean"))
			.contains(false);
	}

	@Test
	void remove() {
		final var metadata = getMetadata();

		final var removed = metadata.remove("string");
		assertEquals("hello", removed);
	}

	@Test
	void size() {
		final var metadata = getMetadata();

		assertEquals(4, metadata.size());

		metadata.remove("string");

		assertEquals(3, metadata.size());
	}

	@Test
	void toAndFromMap() {
		final var metadata = CustomMetadata.fromMap(Map.of(
			"string", "hello",
			"number", 42L,
			"decimal", 4.2d,
			"boolean", true,
			"array", List.of(),
			"object", Map.of()
		));

		assertEquals(4, metadata.size());

		assertThat(metadata.getString("string"))
			.contains("hello");

		assertThat(metadata.getNumber("number"))
			.contains(42l);

		assertThat(metadata.getDecimal("decimal"))
			.contains(4.2d);

		assertThat(metadata.getBoolean("boolean"))
			.contains(true);

		final var map = metadata.toMap();

		assertEquals(4, map.size());

		assertThat(map)
			.containsEntry("string", "hello")
			.containsEntry("number", 42l)
			.containsEntry("decimal", 4.2d)
			.containsEntry("boolean", true);
	}

}