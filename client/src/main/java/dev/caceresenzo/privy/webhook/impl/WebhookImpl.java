package dev.caceresenzo.privy.webhook.impl;

import java.net.http.HttpHeaders;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.svix.exceptions.WebhookVerificationException;

import dev.caceresenzo.privy.model.Event;
import dev.caceresenzo.privy.util.PrivyUtils;
import dev.caceresenzo.privy.webhook.PrivyWebhook;
import dev.caceresenzo.privy.webhook.PrivyWebhookException;

public class WebhookImpl implements PrivyWebhook {

	public static final String ID_HEADER = "svix-id";
	public static final String ID_TIMESTAMP = "svix-timestamp";
	public static final String ID_SIGNATURE = "svix-signature";

	private final ObjectMapper objectMapper;
	private final com.svix.Webhook svixWebhook;

	public WebhookImpl(
		String signingKey
	) {
		Objects.requireNonNull(signingKey, "signingKey must be specified");

		this.objectMapper = PrivyUtils.createMapper();
		this.svixWebhook = new com.svix.Webhook(signingKey);
	}

	@Override
	public Event verify(Headers headers, String body) {
		try {
			svixWebhook.verify(body, toHttpHeaders(headers));
		} catch (WebhookVerificationException exception) {
			throw new PrivyWebhookException.InvalidSignature("signature cannot be verified", exception);
		}

		try {
			return objectMapper.readValue(body, Event.class);
		} catch (JsonProcessingException exception) {
			throw new PrivyWebhookException.PayloadCannotBeRead("payload cannot be read", exception);
		}
	}

	public static HttpHeaders toHttpHeaders(Headers headers) {
		return HttpHeaders.of(
			Map.of(
				ID_HEADER, List.of(headers.id()),
				ID_TIMESTAMP, List.of(headers.timestamp()),
				ID_SIGNATURE, List.of(headers.signature())
			),
			(key, value) -> true
		);
	}

}