package dev.caceresenzo.privy.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = PrivyProperties.PREFIX)
public class PrivyProperties {

	public static final String PREFIX = "privy";
	public static final String PREFIX_APPLICATION_ID = PREFIX + ".application-id";

	private String apiUrl;
	private String applicationId;
	private String applicationSecret;
	private Long maxPageSize;

}