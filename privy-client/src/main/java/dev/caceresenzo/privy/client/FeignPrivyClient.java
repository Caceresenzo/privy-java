package dev.caceresenzo.privy.client;

import dev.caceresenzo.privy.model.ApplicationSettings;
import dev.caceresenzo.privy.model.User;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface FeignPrivyClient {

	public static final String JSON_CONTENT_TYPE = "Content-Type: application/json";

	@RequestLine("GET /api/v1/users/{id}")
	User getUserById(@Param String id);

	@RequestLine("POST /api/v1/users/email/address")
	@Headers(JSON_CONTENT_TYPE)
	User getUserByEmail(AddressRequest body);

	@RequestLine("POST /api/v1/users/wallet/address")
	@Headers(JSON_CONTENT_TYPE)
	User getUserByWallet(AddressRequest body);

	@RequestLine("POST /api/v1/users/phone/number")
	@Headers(JSON_CONTENT_TYPE)
	User getUserByPhone(PhoneRequest body);

	@RequestLine("GET /api/v1/apps/{applicationId}")
	ApplicationSettings getApplicationSettings(@Param String applicationId);

	static record AddressRequest(String address) {}

	static record PhoneRequest(String number) {}

}