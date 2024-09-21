package dev.caceresenzo.privy;

import lombok.experimental.StandardException;

@StandardException
@SuppressWarnings("serial")
public class PrivyException extends RuntimeException {

	@StandardException
	public static class InvalidApplicationId extends PrivyException {

		public static final String MESSAGE = "Invalid Privy app ID";

	}

	@StandardException
	public static class InvalidApplicationSecret extends PrivyException {

		public static final String MESSAGE = "Invalid app ID or app secret.";

	}

	@StandardException
	public static class UserNotFound extends PrivyException {

		public static final String MESSAGE = "User not found";

	}

	@StandardException
	public static class InvalidEmailAddress extends PrivyException {

		public static final String MESSAGE = "[Input error] `address`: Invalid email address";

	}

	@StandardException
	public static class InvalidPhoneNumber extends PrivyException {

		public static final String MESSAGE = "[Input error] `number`: Phone number is not valid";

	}

	@StandardException
	public static class InvalidWalletAddress extends PrivyException {

		public static final String MESSAGE = "[Input error] `address`: Invalid Ethereum address";

	}

	@StandardException
	public static class InvalidTwitterUsernameAddress extends PrivyException {

		public static final String MESSAGE_PREFIX = "Twitter user with username ";

	}

	@StandardException
	public static class InvalidTwitterSubjectAddress extends PrivyException {

		public static final String MESSAGE_PREFIX = "Twitter user with subject ";

	}

}