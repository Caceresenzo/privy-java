package dev.caceresenzo.privy.client;

import dev.caceresenzo.privy.PrivyException;
import lombok.experimental.StandardException;

@StandardException
@SuppressWarnings("serial")
public class PrivyClientException extends PrivyException {

	@StandardException
	public static class InvalidApplicationId extends PrivyClientException {}

	@StandardException
	public static class InvalidApplicationSecret extends PrivyClientException {}

	@StandardException
	public static class UserNotFound extends PrivyClientException {}

	@StandardException
	public static class InvalidEmailAddress extends PrivyClientException {}

	@StandardException
	public static class InvalidPhoneNumber extends PrivyClientException {}

	@StandardException
	public static class InvalidWalletAddress extends PrivyClientException {}

}