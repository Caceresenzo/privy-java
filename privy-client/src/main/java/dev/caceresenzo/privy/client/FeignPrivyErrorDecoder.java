package dev.caceresenzo.privy.client;

import java.util.Map;
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

	private static final Map<String, BiFunction<String, Exception, PrivyException>> MAPPERS = Map.of(
		PrivyException.InvalidApplicationSecret.MESSAGE, PrivyException.InvalidApplicationSecret::new,
		PrivyException.InvalidApplicationId.MESSAGE, PrivyException.InvalidApplicationId::new,
		PrivyException.UserNotFound.MESSAGE, PrivyException.UserNotFound::new,
		PrivyException.InvalidEmailAddress.MESSAGE, PrivyException.InvalidEmailAddress::new,
		PrivyException.InvalidPhoneNumber.MESSAGE, PrivyException.InvalidPhoneNumber::new,
		PrivyException.InvalidWalletAddress.MESSAGE, PrivyException.InvalidWalletAddress::new
	);

	private final ObjectMapper objectMapper;

	@Override
	public Exception decode(String methodKey, Response response) {
		final var exception = (FeignException) super.decode(methodKey, response);

		if (exception instanceof RetryableException) {
			return exception;
		}

		final var message = extractMessage(exception);

		final var mapper = MAPPERS.get(message);
		if (mapper != null) {
			throw mapper.apply(message, exception);
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

}