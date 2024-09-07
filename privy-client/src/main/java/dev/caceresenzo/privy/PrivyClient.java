package dev.caceresenzo.privy;

import java.util.Optional;
import java.util.stream.Stream;

import dev.caceresenzo.privy.client.PrivyClientImpl;
import dev.caceresenzo.privy.model.ApplicationSettings;
import dev.caceresenzo.privy.model.User;
import lombok.Data;
import lombok.experimental.Accessors;

public interface PrivyClient {

	Stream<User> findAllUsers();

	Stream<User> findAllUsers(String search);

	Optional<User> findUserById(String id);

	Optional<User> findUserByEmail(String address);

	Optional<User> findUserByWallet(String address);

	Optional<User> findUserByPhone(String number);

	ApplicationSettings getApplicationSettings();

	public static Builder builder() {
		return new Builder();
	}

	@Data
	@Accessors(fluent = true)
	public static class Builder {

		public static final String DEFAULT_API_URL = "https://auth.privy.io";
		public static final long DEFAULT_MAX_PAGE_SIZE = 100;

		private String apiUrl = DEFAULT_API_URL;
		private String applicationId;
		private String applicationSecret;
		private long maxPageSize = DEFAULT_MAX_PAGE_SIZE;

		public PrivyClient build() {
			return new PrivyClientImpl(
				apiUrl,
				applicationId,
				applicationSecret,
				maxPageSize
			);
		}

	}

}