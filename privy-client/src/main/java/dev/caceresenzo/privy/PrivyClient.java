package dev.caceresenzo.privy;

import java.util.Optional;

import dev.caceresenzo.privy.client.PrivyClientImpl;
import dev.caceresenzo.privy.model.ApplicationSettings;
import dev.caceresenzo.privy.model.User;
import lombok.Data;
import lombok.experimental.Accessors;

public interface PrivyClient {

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

		private String apiUrl = DEFAULT_API_URL;
		private String applicationId;
		private String applicationSecret;

		public PrivyClient build() {
			return new PrivyClientImpl(apiUrl, applicationId, applicationSecret);
		}

	}

}