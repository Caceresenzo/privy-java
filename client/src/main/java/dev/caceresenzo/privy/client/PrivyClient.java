package dev.caceresenzo.privy.client;

import java.security.PublicKey;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import dev.caceresenzo.privy.PrivyException;
import dev.caceresenzo.privy.client.impl.PrivyClientImpl;
import dev.caceresenzo.privy.model.ApplicationSettings;
import dev.caceresenzo.privy.model.CustomMetadata;
import dev.caceresenzo.privy.model.User;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
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
	* Get the user object associated with a Github username.
	*
	* @param username Github username to search for.
	* @returns A {@link User user}, if it exists.
	*/
	Optional<User> findUserByGithubUsername(String username);

	/**
	 * Add customMetadata field to the user object.
	 * 
	 * @param userId DID of the user to set custom metadata for.
	 * @param metadata the custom metadata to set for the user.
	 * @return {@link User User} object updated with custom metadata.
	 * @throws PrivyClientException.UserNotFound If the user does not exist.
	 */
	User setCustomMetadata(String userId, CustomMetadata metadata);

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
	 * Get the auth token verification key.
	 *
	 * @return The verification key.
	 */
	PublicKey getVerificationKey();

	/**
	 * Gets a user from the identity token. First, this verifies the token is valid and then parses the payload into a {@link User} object. <br />
	 * Note the user object may be incomplete due to identity token size constraints. <br />
	
	 * @param idToken The identity token set as a cookie on the users browser.
	 * @return {@link User User} object with parsed from the ID token.
	 * @implNote A cached verification key may be returned.
	 * @throws PrivyJwtException If the ID token is malformed, invalid or expired.
	 */
	User getUserFromIdToken(String idToken);

	/**
	 * Create a new builder.
	 *
	 * @return A new {@link Builder} instance.
	 */
	static Builder builder() {
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

		/** Cache the verification key on first fetch on the client instance. */
		private boolean cacheVerificationKey = true;

		/** Customize the {@link JwtParser JWT Parser} by customizing the {@link JwtParserBuilder builder}. */
		private UnaryOperator<JwtParserBuilder> jwtParserCustomizer = UnaryOperator.identity();

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
				maxPageSize,
				cacheVerificationKey,
				jwtParserCustomizer
			);
		}

	}

}