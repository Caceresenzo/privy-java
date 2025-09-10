package dev.caceresenzo.privy.spring.boot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.caceresenzo.privy.client.PrivyClient;
import dev.caceresenzo.privy.client.impl.PrivyClientImpl;
import dev.caceresenzo.privy.spring.boot.autoconfigure.PrivyAutoConfiguration;
import dev.caceresenzo.privy.spring.boot.autoconfigure.PrivyJwtParserCustomizer;
import dev.caceresenzo.privy.spring.boot.autoconfigure.PrivyProperties;
import io.jsonwebtoken.impl.DefaultJwtParser;
import lombok.SneakyThrows;

@SpringBootTest(
	classes = {
		PrivyAutoConfiguration.class,
		ClientAutoConfigurationTest.ContextConfiguration.class,
	},
	properties = {
		PrivyProperties.PREFIX_APPLICATION_ID + "=hello",
		PrivyProperties.PREFIX_APPLICATION_SECRET + "=world"
	}
)
class ClientAutoConfigurationTest {

	@Test
	@SneakyThrows
	void contextLoads(@Autowired PrivyClient client) {
		final var clientImpl = assertInstanceOf(PrivyClientImpl.class, client);
		final var jwtParser = assertInstanceOf(DefaultJwtParser.class, clientImpl.getJwtParser());

		final var clockSkew = ReflectionUtils.tryToReadFieldValue(DefaultJwtParser.class, "allowedClockSkewMillis", jwtParser);
		assertEquals(42_000l, clockSkew.get());
	}

	@Configuration
	static class ContextConfiguration {

		@Bean
		public PrivyJwtParserCustomizer clockSkewJwtParserCustomizer() {
			return (builder) -> builder.clockSkewSeconds(42);
		}

	}

}