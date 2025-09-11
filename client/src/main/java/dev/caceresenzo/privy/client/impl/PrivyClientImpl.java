package dev.caceresenzo.privy.client.impl;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.caceresenzo.privy.client.PrivyClient;
import dev.caceresenzo.privy.client.PrivyClientException;
import dev.caceresenzo.privy.client.impl.FeignPrivyClient.AddressRequest;
import dev.caceresenzo.privy.client.impl.FeignPrivyClient.CustomMetadataUpdateRequest;
import dev.caceresenzo.privy.client.impl.FeignPrivyClient.CustomUserIdRequest;
import dev.caceresenzo.privy.client.impl.FeignPrivyClient.PhoneRequest;
import dev.caceresenzo.privy.client.impl.FeignPrivyClient.SubjectRequest;
import dev.caceresenzo.privy.client.impl.FeignPrivyClient.UsernameRequest;
import dev.caceresenzo.privy.client.impl.auth.AuthRequestInterceptor;
import dev.caceresenzo.privy.client.impl.pagination.PageSpliterator;
import dev.caceresenzo.privy.model.ApplicationSettings;
import dev.caceresenzo.privy.model.CustomMetadata;
import dev.caceresenzo.privy.model.LinkedAccount;
import dev.caceresenzo.privy.model.User;
import dev.caceresenzo.privy.util.PrivyUtils;
import dev.caceresenzo.privy.util.serial.UnixDateDeserializer;
import feign.Feign;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public class PrivyClientImpl implements PrivyClient {

	private static final CustomMetadata EMPTY_METADATA = CustomMetadata.fromValues(Collections.emptyMap());

	private final String applicationId;
	private final long maxPageSize;
	private final boolean cacheVerificationKey;
	private final JwtParser jwtParser;

	private final ObjectMapper objectMapper;
	private final FeignPrivyClient delegate;

	private PublicKey cachedVerificationKey = null;

	public PrivyClientImpl(
		String apiUrl,
		String applicationId,
		String applicationSecret,
		long maxPageSize,
		boolean cacheVerificationKey,
		UnaryOperator<JwtParserBuilder> jwtParserCustomizer
	) {
		Objects.requireNonNull(apiUrl, "apiUrl must be specified");
		Objects.requireNonNull(applicationId, "applicationId must be specified");
		Objects.requireNonNull(applicationSecret, "applicationSecret must be specified");

		if (maxPageSize < 1) {
			throw new IllegalArgumentException("maxPageSize must be positive");
		}

		this.applicationId = applicationId;
		this.maxPageSize = maxPageSize;
		this.cacheVerificationKey = cacheVerificationKey;

		this.jwtParser = jwtParserCustomizer
			.apply(Jwts.parser())
			.keyLocator((__) -> getVerificationKey())
			.requireAudience(applicationId)
			.requireIssuer("privy.io")
			.build();

		this.objectMapper = PrivyUtils.createMapper();
		this.delegate = Feign.builder()
			.encoder(new JacksonEncoder(this.objectMapper))
			.decoder(new JacksonDecoder(this.objectMapper))
			.requestInterceptor(new AuthRequestInterceptor(applicationId, applicationSecret))
			.errorDecoder(new FeignPrivyErrorDecoder(this.objectMapper))
			.retryer(Retryer.NEVER_RETRY)
			.target(FeignPrivyClient.class, apiUrl);
	}

	@Override
	public Stream<User> findAllUsers() {
		final var firstPage = delegate.getUsers(maxPageSize);

		return new PageSpliterator<>(
			firstPage,
			(nextCursor) -> delegate.getUsers(maxPageSize, nextCursor)
		).asStream();
	}

	@Override
	public Stream<User> findAllUsers(String searchTerm) {
		final var body = new FeignPrivyClient.SearchRequest(
			searchTerm,
			maxPageSize,
			null
		);

		final var firstPage = delegate.searchUsers(body);

		return new PageSpliterator<>(
			firstPage,
			(nextCursor) -> delegate.searchUsers(body.withCursor(nextCursor))
		).asStream();
	}

	@Override
	public Optional<User> findUserById(String id) {
		if (PrivyUtils.isBlank(id)) {
			return Optional.empty();
		}

		try {
			return Optional.of(delegate.getUserById(id));
		} catch (PrivyClientException.UserNotFound __) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<User> findUserByEmail(String address) {
		if (PrivyUtils.isBlank(address)) {
			return Optional.empty();
		}

		try {
			return Optional.of(delegate.getUserByEmail(new AddressRequest(address)));
		} catch (PrivyClientException.UserNotFound __) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<User> findUserByWallet(String address) {
		if (PrivyUtils.isBlank(address)) {
			return Optional.empty();
		}

		try {
			return Optional.of(delegate.getUserByWallet(new AddressRequest(address)));
		} catch (PrivyClientException.UserNotFound __) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<User> findUserByPhone(String number) {
		if (PrivyUtils.isBlank(number)) {
			return Optional.empty();
		}

		try {
			return Optional.of(delegate.getUserByPhone(new PhoneRequest(number)));
		} catch (PrivyClientException.UserNotFound __) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<User> findUserByTwitterUsername(String username) {
		if (PrivyUtils.isBlank(username)) {
			return Optional.empty();
		}

		try {
			return Optional.of(delegate.getUserByTwitterUsername(new UsernameRequest(username)));
		} catch (PrivyClientException.UserNotFound __) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<User> findUserByTwitterSubject(String subject) {
		if (PrivyUtils.isBlank(subject)) {
			return Optional.empty();
		}

		try {
			return Optional.of(delegate.getUserByTwitterSubject(new SubjectRequest(subject)));
		} catch (PrivyClientException.UserNotFound __) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<User> findUserByDiscordUsername(String username) {
		if (PrivyUtils.isBlank(username)) {
			return Optional.empty();
		}

		try {
			return Optional.of(delegate.getUserByDiscordUsername(new UsernameRequest(username)));
		} catch (PrivyClientException.UserNotFound __) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<User> findUserByGithubUsername(String username) {
		if (PrivyUtils.isBlank(username)) {
			return Optional.empty();
		}

		try {
			return Optional.of(delegate.getUserByGithubUsername(new UsernameRequest(username)));
		} catch (PrivyClientException.UserNotFound __) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<User> findUserByCustomAuthId(String customUserId) {
		if (PrivyUtils.isBlank(customUserId)) {
			return Optional.empty();
		}

		try {
			return Optional.of(delegate.getUserByCustomAuthId(new CustomUserIdRequest(customUserId)));
		} catch (PrivyClientException.UserNotFound __) {
			return Optional.empty();
		}
	}

	@Override
	public User setCustomMetadata(String userId, CustomMetadata metadata) {
		if (metadata == null) {
			metadata = EMPTY_METADATA;
		}

		return delegate.setCustomMetadata(userId, new CustomMetadataUpdateRequest(metadata));
	}

	@Override
	public boolean deleteUserById(String id) {
		if (PrivyUtils.isBlank(id)) {
			return false;
		}

		try {
			delegate.deleteUserById(id);

			return true;
		} catch (PrivyClientException.UserNotFound __) {
			return false;
		}
	}

	@Override
	public ApplicationSettings getApplicationSettings() {
		final var applicationSettings = delegate.getApplicationSettings(applicationId);

		if (cacheVerificationKey) {
			cachedVerificationKey = parsePublicKey(applicationSettings);
		}

		return applicationSettings;
	}

	@SneakyThrows
	@Override
	public PublicKey getVerificationKey() {
		if (cacheVerificationKey && cachedVerificationKey != null) {
			return cachedVerificationKey;
		}

		final var applicationSettings = delegate.getApplicationSettings(applicationId);
		final var publicKey = parsePublicKey(applicationSettings);

		if (cacheVerificationKey) {
			cachedVerificationKey = publicKey;
		}

		return publicKey;
	}

	@SneakyThrows
	private PublicKey parsePublicKey(ApplicationSettings applicationSettings) {
		final var publicKeyString = applicationSettings.getVerificationKey()
			.replace("-----BEGIN PUBLIC KEY-----", "")
			.replace("-----END PUBLIC KEY-----", "")
			.replaceAll("\\s", "");

		final var publicKeyBytes = Base64.getDecoder().decode(publicKeyString);

		final var keySpec = new X509EncodedKeySpec(publicKeyBytes);
		final var keyFactory = KeyFactory.getInstance("EC");

		return keyFactory.generatePublic(keySpec);
	}

	@Override
	public Jws<Claims> verifyAuthToken(String token) {
		return jwtParser.parseSignedClaims(token);
	}

	@Override
	public User getUserFromIdToken(String idToken) {
		final var jwt = verifyAuthToken(idToken);
		final var payload = jwt.getPayload();

		final var linkedAccountsJson = payload.get("linked_accounts");
		if (!(linkedAccountsJson instanceof String linkedAccountsString)) {
			throw new MalformedJwtException("linked_accounts is not a string");
		}

		final List<LinkedAccount> linkedAccounts;
		try {
			linkedAccounts = objectMapper.readValue(linkedAccountsString, PrivyUtils.LINKED_ACCOUNT_LIST_TYPE_REFERENCE);
		} catch (JsonProcessingException exception) {
			throw new MalformedJwtException("failed to parse linked accounts", exception);
		}

		CustomMetadata customMetadata = null;
		if (payload.get("custom_metadata") instanceof String customMetadataString) {
			try {
				customMetadata = objectMapper.readValue(customMetadataString, CustomMetadata.class);
			} catch (JsonProcessingException exception) {
				throw new MalformedJwtException("failed to parse linked accounts", exception);
			}
		}

		final var user = new User();
		user.setId(payload.getSubject());
		user.setLinkedAccounts(linkedAccounts);
		user.setGuest("t".equals(payload.get("guest")));
		user.setCustomMetadata(customMetadata);
		user.setCreatedAt(UnixDateDeserializer.fromTimestamp(Long.valueOf(payload.get("cr", String.class))));

		return user;
	}

}