package dev.caceresenzo.privy;

import lombok.experimental.StandardException;

@StandardException
@SuppressWarnings("serial")
public class PrivyException extends RuntimeException {

	@StandardException
	public static class InvalidApplicationId extends PrivyException {}

	@StandardException
	public static class InvalidApplicationSecret extends PrivyException {}

	@StandardException
	public static class UserNotFound extends PrivyException {}

	@StandardException
	public static class InvalidEmailAddress extends PrivyException {}

	@StandardException
	public static class InvalidPhoneNumber extends PrivyException {}

	@StandardException
	public static class InvalidWalletAddress extends PrivyException {}

}