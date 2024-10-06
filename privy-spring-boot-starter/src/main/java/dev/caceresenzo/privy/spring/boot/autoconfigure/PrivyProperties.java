package dev.caceresenzo.privy.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = PrivyProperties.PREFIX)
public class PrivyProperties {

	public static final String PREFIX = "privy";
	public static final String PREFIX_APPLICATION_ID = PREFIX + ".application-id";
	public static final String PREFIX_APPLICATION_SECRET = PREFIX + ".application-secret";
	public static final String PREFIX_WEBHOOK_SIGNING_KEY = PREFIX + ".webhook-signing-key";

	private String apiUrl;
	private String applicationId;
	private String applicationSecret;
	private String webhookSigningKey;
	private Long maxPageSize;

}