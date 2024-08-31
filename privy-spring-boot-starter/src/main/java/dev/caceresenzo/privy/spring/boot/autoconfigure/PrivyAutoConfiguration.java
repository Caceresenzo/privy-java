package dev.caceresenzo.privy.spring.boot.autoconfigure;

import java.io.IOException;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.caceresenzo.privy.PrivyClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(PrivyClient.class)
@EnableConfigurationProperties(PrivyProperties.class)
public class PrivyAutoConfiguration {

	@Bean
	@ConditionalOnProperty(PrivyProperties.PREFIX_APPLICATION_ID)
	@ConditionalOnMissingBean
	PrivyClient privyClient(PrivyProperties properties) throws IOException {
		log.info("Configuring Privy");

		final PrivyClient.Builder builder = PrivyClient.builder()
			.applicationId(properties.getApplicationId())
			.applicationSecret(properties.getApplicationSecret());

		final String apiUrl = properties.getApiUrl();
		if (apiUrl != null) {
			builder.apiUrl(apiUrl);
		}

		return builder.build();
	}

}