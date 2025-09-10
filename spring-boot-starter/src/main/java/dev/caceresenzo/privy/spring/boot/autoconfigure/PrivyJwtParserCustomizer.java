package dev.caceresenzo.privy.spring.boot.autoconfigure;

import io.jsonwebtoken.JwtParserBuilder;

@FunctionalInterface
public interface PrivyJwtParserCustomizer {

	JwtParserBuilder customize(JwtParserBuilder jwtParserBuilder);

}