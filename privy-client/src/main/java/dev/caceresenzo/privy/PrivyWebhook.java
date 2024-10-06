package dev.caceresenzo.privy;

import dev.caceresenzo.privy.model.Event;
import dev.caceresenzo.privy.webhook.WebhookImpl;
import lombok.Data;
import lombok.experimental.Accessors;

public interface PrivyWebhook {

	/**
	 * Verifies a webhook request by checking the signature and asserting the timestamp is within 5 minutes of the current time to prevent replay attacks.
	 *
	 * @param headers An object containing the webhook's ID, timestamp, and signature sent in the headers of the webhook request.
	 * @param body The raw JSON payload/body of the webhook request. This must be unaltered or signature verification will fail.
	 * @returns A verified payload if the webhook signature is valid otherwise throws.
	 */
	Event verify(Headers headers, String body);

	public static record Headers(
		String id,
		String timestamp,
		String signature
	) {}

	/**
	 * Create a new builder.
	 * 
	 * @return A new {@link Builder} instance.
	 */
	public static Builder builder() {
		return new Builder();
	}

	@Data
	@Accessors(fluent = true)
	public static class Builder {

		/** The webhook signing key used for validation, only visible in the console. */
		private String signingKey;

		/**
		 * Build the webhook.
		 * 
		 * @return A configured webhook instance.
		 */
		public PrivyWebhook build() {
			return new WebhookImpl(
				signingKey
			);
		}

	}

}