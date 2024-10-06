package dev.caceresenzo.privy;

import lombok.experimental.StandardException;

@StandardException
@SuppressWarnings("serial")
public class PrivyWebhookException extends PrivyException {

	@StandardException
	public static class InvalidSignature extends PrivyWebhookException {}
	
	@StandardException
	public static class PayloadCannotBeRead extends PrivyWebhookException {}

}