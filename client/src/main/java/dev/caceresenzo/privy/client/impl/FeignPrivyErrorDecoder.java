package dev.caceresenzo.privy.client.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.caceresenzo.privy.client.PrivyClientException;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FeignPrivyErrorDecoder extends ErrorDecoder.Default {

	private final ObjectMapper objectMapper;
	private final List<ErrorMapper> mappers;

	public FeignPrivyErrorDecoder(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;

		this.mappers = new ArrayList<>();
		{
			this.mappers.add(ErrorMapper.equals("Invalid Privy app ID", PrivyClientException.InvalidApplicationId::new));
			this.mappers.add(ErrorMapper.equals("Invalid app ID or app secret.", PrivyClientException.InvalidApplicationSecret::new));

			this.mappers.add(ErrorMapper.equals("User not found", PrivyClientException.UserNotFound::new));
			this.mappers.add(ErrorMapper.equals("User not found with provided email.", PrivyClientException.UserNotFound::new));
			this.mappers.add(ErrorMapper.equals("User not found with provided Wallet address.", PrivyClientException.UserNotFound::new));
			this.mappers.add(ErrorMapper.equals("User not found with provided phone number.", PrivyClientException.UserNotFound::new));
			this.mappers.add(ErrorMapper.equals("User not found with provided Twitter username.", PrivyClientException.UserNotFound::new));
			this.mappers.add(ErrorMapper.equals("User not found with provided Twitter subject.", PrivyClientException.UserNotFound::new));
			this.mappers.add(ErrorMapper.equals("Discord user not found for provided username.", PrivyClientException.UserNotFound::new));
			this.mappers.add(ErrorMapper.equals("Github user not found for provided username.", PrivyClientException.UserNotFound::new));

			this.mappers.add(ErrorMapper.equals("[Input error] `address`: Invalid email address", PrivyClientException.InvalidEmailAddress::new));
			this.mappers.add(ErrorMapper.equals("[Input error] `number`: Phone number is not valid", PrivyClientException.InvalidPhoneNumber::new));
			this.mappers.add(ErrorMapper.equals("[Input error] `address`: Invalid Ethereum address", PrivyClientException.InvalidWalletAddress::new));

			this.mappers.add(ErrorMapper.startsWith("[Input error] `custom_metadata", PrivyClientException.InvalidCustomMetadata::new));
			this.mappers.add(ErrorMapper.equals("Size of custom metadata object too big (>1KB)", PrivyClientException.InvalidCustomMetadata::new));
		}
	}

	@Override
	public Exception decode(String methodKey, Response response) {
		final var exception = (FeignException) super.decode(methodKey, response);

		if (exception instanceof RetryableException) {
			return exception;
		}

		final var message = extractMessage(exception);
		for (final var mapper : mappers) {
			if (mapper.match(message)) {
				throw mapper.map(message, exception);
			}
		}

		throw new PrivyClientException(message, exception);
	}

	public String extractMessage(FeignException exception) {
		try {
			final var responseBody = exception.responseBody().orElseThrow();

			final var dto = objectMapper.readValue(
				responseBody.array(),
				responseBody.arrayOffset(),
				responseBody.limit(),
				ErrorDto.class
			);

			return dto.message();
		} catch (Exception __) {
			return null;
		}
	}

	public static record ErrorDto(@JsonProperty("error") String message) {}

	public interface ErrorMapper {

		boolean match(String message);

		PrivyClientException map(String message, Exception cause);

		static ErrorMapper equals(String exactMessage, BiFunction<String, Exception, PrivyClientException> mapper) {
			return new ErrorMapper() {

				@Override
				public boolean match(String message) {
					return exactMessage.equalsIgnoreCase(message);
				}

				@Override
				public PrivyClientException map(String message, Exception cause) {
					return mapper.apply(message, cause);
				}

			};
		}

		static ErrorMapper startsWith(String prefix, BiFunction<String, Exception, PrivyClientException> mapper) {
			return new ErrorMapper() {

				@Override
				public boolean match(String message) {
					if (message == null) {
						return false;
					}

					return message.startsWith(prefix);
				}

				@Override
				public PrivyClientException map(String message, Exception cause) {
					return mapper.apply(message, cause);
				}

			};
		}

	}

}