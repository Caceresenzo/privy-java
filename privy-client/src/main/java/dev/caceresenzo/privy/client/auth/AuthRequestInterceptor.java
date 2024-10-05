package dev.caceresenzo.privy.client.auth;

import feign.RequestTemplate;
import feign.auth.BasicAuthRequestInterceptor;

public class AuthRequestInterceptor extends BasicAuthRequestInterceptor {

	public static final String APPLICATION_ID_HEADER = "privy-app-id";
	public static final String CLIENT_HEADER = "privy-client";

	private final String applicationId;

	public AuthRequestInterceptor(String applicationId, String applicationSecret) {
		super(applicationId, applicationSecret);

		this.applicationId = applicationId;
	}

	@Override
	public void apply(RequestTemplate template) {
		super.apply(template);

		template.header(APPLICATION_ID_HEADER, applicationId);
		template.header(CLIENT_HEADER, "privy-java");
	}

}