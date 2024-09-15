package dev.caceresenzo.privy;

import lombok.experimental.StandardException;

@StandardException
@SuppressWarnings("serial")
public class PrivyException extends RuntimeException {

	@StandardException
	public static class UserNotFound extends PrivyException {

		public static final String MESSAGE = "User not found";

	}

}