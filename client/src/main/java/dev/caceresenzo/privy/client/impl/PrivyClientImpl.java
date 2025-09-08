package dev.caceresenzo.privy.client.impl;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import dev.caceresenzo.privy.client.PrivyClient;
import dev.caceresenzo.privy.client.PrivyClientException;
import dev.caceresenzo.privy.client.impl.FeignPrivyClient.AddressRequest;
import dev.caceresenzo.privy.client.impl.FeignPrivyClient.CustomMetadataUpdateRequest;
import dev.caceresenzo.privy.client.impl.FeignPrivyClient.PhoneRequest;
import dev.caceresenzo.privy.client.impl.FeignPrivyClient.SubjectRequest;
import dev.caceresenzo.privy.client.impl.FeignPrivyClient.UsernameRequest;
import dev.caceresenzo.privy.client.impl.auth.AuthRequestInterceptor;
import dev.caceresenzo.privy.client.impl.pagination.PageSpliterator;
import dev.caceresenzo.privy.model.ApplicationSettings;
import dev.caceresenzo.privy.model.CustomMetadata;
import dev.caceresenzo.privy.model.User;
import dev.caceresenzo.privy.util.PrivyUtils;
import feign.Feign;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.SneakyThrows;

public class PrivyClientImpl implements PrivyClient {

	private static final CustomMetadata EMPTY_METADATA = CustomMetadata.fromValues(Collections.emptyMap());

	private final String applicationId;
	private final long maxPageSize;
	private final FeignPrivyClient delegate;

	public PrivyClientImpl(
		String apiUrl,
		String applicationId,
		String applicationSecret,
		long maxPageSize
	) {
		Objects.requireNonNull(apiUrl, "apiUrl must be specified");
		Objects.requireNonNull(applicationId, "applicationId must be specified");
		Objects.requireNonNull(applicationSecret, "applicationSecret must be specified");

		if (maxPageSize < 1) {
			throw new IllegalArgumentException("maxPageSize must be positive");
		}

		final var mapper = PrivyUtils.createMapper();

		this.applicationId = applicationId;
		this.maxPageSize = maxPageSize;
		this.delegate = Feign.builder()
			.encoder(new JacksonEncoder(mapper))
			.decoder(new JacksonDecoder(mapper))
			.requestInterceptor(new AuthRequestInterceptor(applicationId, applicationSecret))
			.errorDecoder(new FeignPrivyErrorDecoder(mapper))
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
		if (id == null) {
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
		if (address == null) {
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
		if (address == null) {
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
		if (number == null) {
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
		if (username == null) {
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
		if (subject == null) {
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
		if (username == null) {
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
		if (username == null) {
			return Optional.empty();
		}

		try {
			return Optional.of(delegate.getUserByGithubUsername(new UsernameRequest(username)));
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
		if (id == null) {
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
		return delegate.getApplicationSettings(applicationId);
	}

	@SneakyThrows
	@Override
	public PublicKey getVerificationKey() {
		final var publicKeyString = getApplicationSettings()
			.getVerificationKey()
			.replace("-----BEGIN PUBLIC KEY-----", "")
			.replace("-----END PUBLIC KEY-----", "")
			.replaceAll("\\s", "");

		final var publicKeyBytes = Base64.getDecoder().decode(publicKeyString);

		final var keySpec = new X509EncodedKeySpec(publicKeyBytes);
		final var keyFactory = KeyFactory.getInstance("EC");
		return keyFactory.generatePublic(keySpec);
	}

}