package dev.caceresenzo.privy;

import java.util.Optional;
import java.util.stream.Stream;

import dev.caceresenzo.privy.client.PrivyClientImpl;
import dev.caceresenzo.privy.model.ApplicationSettings;
import dev.caceresenzo.privy.model.User;
import lombok.Data;
import lombok.experimental.Accessors;

public interface PrivyClient {

	/**
	 * Get the users associated with this application.
	 *
	 * @return A {@link Stream stream} of {@link User users}.
	 */
	Stream<User> findAllUsers();

	/**
	 * Get the users associated with this application whose email, phone number, wallet address, name, or username match a search term.
	 *
	 * @param searchTerm The value used to search the list of users.
	 * @return A {@link Stream stream} of {@link User users}.
	 */
	Stream<User> findAllUsers(String searchTerm);

	/**
	 * Get the user object associated with the given user DID (decentralized ID).
	 * 
	 * @param id The Privy DID of the user.
	 * @return A {@link User user}, if it exists.
	 */
	Optional<User> findUserById(String id);

	/**
	 * Get the user object associated with an email address.
	 *
	 * @param address The email address of the user to find.
	 * @return A {@link User user}, if it exists.
	 * @throws PrivyException.InvalidEmailAddress If the email address is invalid.
	 */
	Optional<User> findUserByEmail(String address);

	/**
	 * Get the user object associated with a wallet address.
	 *
	 * @param address The wallet address of the user to find.
	 * @returns A {@link User user}, if it exists.
	 * @throws PrivyException.InvalidWalletAddress If the wallet address is invalid.
	 */
	Optional<User> findUserByWallet(String address);

	/**
	 * Get the user object associated with a phone number.
	 *
	 * @param number The phone number of the user to find.
	 * @return A {@link User user}, if it exists.
	 * @throws PrivyException.InvalidPhoneNumber If the phone number is invalid.
	 */
	Optional<User> findUserByPhone(String number);

	/**
	 * Get the user object associated with an OAuth account.
	 *
	 * @param username Twitter username to search for.
	 * @return A {@link User user}, if it exists.
	 */
	Optional<User> findUserByTwitterUsername(String username);

	/**
	 * Get the user object associated with an OAuth account.
	 *
	 * @param subject Twitter subject to search for.
	 * @returns A {@link User user}, if it exists.
	 */
	Optional<User> findUserByTwitterSubject(String subject);

	/**
	 * Get the user object associated with a Discord username.
	 *
	 * @param username Discord username to search for.
	 * @returns A {@link User user}, if it exists.
	 */
	Optional<User> findUserByDiscordUsername(String username);

	/**
	 * Delete the user object associated with the given user DID (decentralized ID).
	 *
	 * @param id The Privy DID of the user.
	 * @return If the user has been deleted.
	 */
	boolean deleteUserById(String id);

	/**
	 * Get the application settings associated with the given application.
	 *
	 * @return The {@link ApplicationSettings}.
	 */
	ApplicationSettings getApplicationSettings();

	/**
	 * Create a new builder.
	 * 
	 * @return A new {@link Builder} instance.
	 */
	public static Builder builder() {
		return new Builder();
	}

	@Data
	@Accessors(fluent = true)
	public static class Builder {

		public static final String DEFAULT_API_URL = "https://auth.privy.io";
		public static final long DEFAULT_MAX_PAGE_SIZE = 100;

		/** The URL of the Privy API. Defaults to `https://auth.privy.io`. */
		private String apiUrl = DEFAULT_API_URL;

		/** The application id from your console. */
		private String applicationId;

		/** The application secret, only visible once on app creation in the console. */
		private String applicationSecret;

		/** The page size used for pagination. */
		private long maxPageSize = DEFAULT_MAX_PAGE_SIZE;

		/**
		 * Build the client.
		 * 
		 * @return A configured client instance.
		 */
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