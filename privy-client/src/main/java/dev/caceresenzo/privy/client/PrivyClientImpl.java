package dev.caceresenzo.privy.client;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import dev.caceresenzo.privy.PrivyClient;
import dev.caceresenzo.privy.auth.AuthRequestInterceptor;
import dev.caceresenzo.privy.client.FeignPrivyClient.AddressRequest;
import dev.caceresenzo.privy.client.FeignPrivyClient.PhoneRequest;
import dev.caceresenzo.privy.model.ApplicationSettings;
import dev.caceresenzo.privy.model.User;
import dev.caceresenzo.privy.serial.UnixDateDeserializer;
import feign.Feign;
import feign.FeignException;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

public class PrivyClientImpl implements PrivyClient {

	private final String applicationId;
	private final FeignPrivyClient delegate;

	public PrivyClientImpl(
		String apiUrl,
		String applicationId,
		String applicationSecret
	) {
		Objects.requireNonNull(apiUrl, "apiUrl must be specified");
		Objects.requireNonNull(applicationId, "applicationId must be specified");
		Objects.requireNonNull(applicationSecret, "applicationSecret must be specified");

		final var mapper = createMapper();

		this.applicationId = applicationId;
		this.delegate = Feign.builder()
			.encoder(new JacksonEncoder(mapper))
			.decoder(new JacksonDecoder(mapper))
			.requestInterceptor(new AuthRequestInterceptor(applicationId, applicationSecret))
			.target(FeignPrivyClient.class, apiUrl);
	}

	@Override
	public Optional<User> findUserById(String id) {
		try {
			return Optional.of(delegate.getUserById(id));
		} catch (FeignException.NotFound __) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<User> findUserByEmail(String address) {
		try {
			return Optional.of(delegate.getUserByEmail(new AddressRequest(address)));
		} catch (FeignException.NotFound __) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<User> findUserByWallet(String address) {
		try {
			return Optional.of(delegate.getUserByWallet(new AddressRequest(address)));
		} catch (FeignException.NotFound __) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<User> findUserByPhone(String number) {
		try {
			return Optional.of(delegate.getUserByPhone(new PhoneRequest(number)));
		} catch (FeignException.NotFound __) {
			return Optional.empty();
		}
	}

	@Override
	public ApplicationSettings getApplicationSettings() {
		return delegate.getApplicationSettings(applicationId);
	}

	public static ObjectMapper createMapper() {
		SimpleModule module = new SimpleModule();
		module.addDeserializer(Date.class, new UnixDateDeserializer());

		return JsonMapper.builder()
			.serializationInclusion(JsonInclude.Include.NON_NULL)
			.configure(SerializationFeature.INDENT_OUTPUT, true)
			.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
			.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.addModule(module)
			.build();
	}

}