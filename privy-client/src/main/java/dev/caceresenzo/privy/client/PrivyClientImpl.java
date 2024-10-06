package dev.caceresenzo.privy.client;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import dev.caceresenzo.privy.PrivyClient;
import dev.caceresenzo.privy.PrivyClientException;
import dev.caceresenzo.privy.client.FeignPrivyClient.AddressRequest;
import dev.caceresenzo.privy.client.FeignPrivyClient.PhoneRequest;
import dev.caceresenzo.privy.client.FeignPrivyClient.SubjectRequest;
import dev.caceresenzo.privy.client.FeignPrivyClient.UsernameRequest;
import dev.caceresenzo.privy.client.auth.AuthRequestInterceptor;
import dev.caceresenzo.privy.client.pagination.PageSpliterator;
import dev.caceresenzo.privy.model.ApplicationSettings;
import dev.caceresenzo.privy.model.User;
import dev.caceresenzo.privy.util.PrivyUtils;
import feign.Feign;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

public class PrivyClientImpl implements PrivyClient {

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

}