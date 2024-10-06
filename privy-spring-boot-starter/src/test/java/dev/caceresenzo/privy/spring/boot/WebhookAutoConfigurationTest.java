package dev.caceresenzo.privy.spring.boot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.caceresenzo.privy.PrivyWebhook;
import dev.caceresenzo.privy.spring.boot.autoconfigure.PrivyAutoConfiguration;
import dev.caceresenzo.privy.spring.boot.autoconfigure.PrivyProperties;

@SpringBootTest(
	classes = {
		PrivyAutoConfiguration.class,
	}, properties = {
		PrivyProperties.PREFIX_WEBHOOK_SIGNING_KEY + "=whsec_helloworld"
	}
)
class WebhookAutoConfigurationTest {

	@Test
	void contextLoads(@Autowired PrivyWebhook webhook) {}

}