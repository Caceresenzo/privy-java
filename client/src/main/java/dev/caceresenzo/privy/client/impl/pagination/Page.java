package dev.caceresenzo.privy.client.impl.pagination;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Page<T>(
	List<T> data,
	@JsonProperty("next_cursor") String nextCursor
) {

	public boolean isEmpty() {
		return data == null || data.isEmpty();
	}

}