package dev.caceresenzo.privy.client;

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

	@Override
	public Exception decode(String methodKey, Response response) {
		final var exception = (FeignException) super.decode(methodKey, response);

		if (exception instanceof RetryableException) {
			return exception;
		}

		final var message = extractMessage(exception);

		if (PrivyException.UserNotFound.MESSAGE.equals(message)) {
			throw new PrivyException.UserNotFound(message);
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