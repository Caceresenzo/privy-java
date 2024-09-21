package dev.caceresenzo.privy.client;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.caceresenzo.privy.PrivyException;
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
		super();

		this.objectMapper = objectMapper;

		this.mappers = new ArrayList<>();
		this.mappers.add(ErrorMapper.equals(PrivyException.InvalidApplicationSecret.MESSAGE, PrivyException.InvalidApplicationSecret::new));
		this.mappers.add(ErrorMapper.equals(PrivyException.InvalidApplicationId.MESSAGE, PrivyException.InvalidApplicationId::new));
		this.mappers.add(ErrorMapper.equals(PrivyException.UserNotFound.MESSAGE, PrivyException.UserNotFound::new));
		this.mappers.add(ErrorMapper.equals(PrivyException.InvalidEmailAddress.MESSAGE, PrivyException.InvalidEmailAddress::new));
		this.mappers.add(ErrorMapper.equals(PrivyException.InvalidPhoneNumber.MESSAGE, PrivyException.InvalidPhoneNumber::new));
		this.mappers.add(ErrorMapper.equals(PrivyException.InvalidWalletAddress.MESSAGE, PrivyException.InvalidWalletAddress::new));
		this.mappers.add(ErrorMapper.startsWith(PrivyException.InvalidTwitterUsernameAddress.MESSAGE_PREFIX, PrivyException.InvalidTwitterUsernameAddress::new));
		this.mappers.add(ErrorMapper.startsWith(PrivyException.InvalidTwitterSubjectAddress.MESSAGE_PREFIX, PrivyException.InvalidTwitterSubjectAddress::new));
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

		throw new PrivyException(message, exception);
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

	public static interface ErrorMapper {

		boolean match(String message);

		PrivyException map(String message, Exception cause);

		public static ErrorMapper equals(String exactMessage, BiFunction<String, Exception, PrivyException> mapper) {
			return new ErrorMapper() {

				@Override
				public boolean match(String message) {
					return exactMessage.equalsIgnoreCase(message);
				}

				@Override
				public PrivyException map(String message, Exception cause) {
					return mapper.apply(message, cause);
				}

			};
		}

		public static ErrorMapper startsWith(String prefix, BiFunction<String, Exception, PrivyException> mapper) {
			return new ErrorMapper() {

				@Override
				public boolean match(String message) {
					if (message == null) {
						return false;
					}

					return message.startsWith(prefix);
				}

				@Override
				public PrivyException map(String message, Exception cause) {
					return mapper.apply(message, cause);
				}

			};
		}

	}

}