package dev.caceresenzo.privy.spring.boot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.caceresenzo.privy.PrivyClient;
import dev.caceresenzo.privy.spring.boot.autoconfigure.PrivyAutoConfiguration;
import dev.caceresenzo.privy.spring.boot.autoconfigure.PrivyProperties;

@SpringBootTest(
	classes = {
		PrivyAutoConfiguration.class,
	}, properties = {
		PrivyProperties.PREFIX_APPLICATION_ID + "=hello",
		PrivyProperties.PREFIX_APPLICATION_SECRET + "=world"
	}
)
class ClientAutoConfigurationTest {

	@Test
	void contextLoads(@Autowired PrivyClient client) {}

}