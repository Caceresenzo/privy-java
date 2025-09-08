package dev.caceresenzo.privy.client;

import dev.caceresenzo.privy.PrivyException;
import lombok.experimental.StandardException;

@StandardException
@SuppressWarnings("serial")
public class PrivyJwtException extends PrivyException {

	@StandardException
	public static class Malformed extends PrivyJwtException {}

}