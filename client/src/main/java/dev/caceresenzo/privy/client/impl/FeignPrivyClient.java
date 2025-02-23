package dev.caceresenzo.privy.client.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.caceresenzo.privy.client.impl.pagination.Page;
import dev.caceresenzo.privy.model.ApplicationSettings;
import dev.caceresenzo.privy.model.CustomMetadata;
import dev.caceresenzo.privy.model.User;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import lombok.With;

public interface FeignPrivyClient {

	String JSON_CONTENT_TYPE = "Content-Type: application/json";

	@RequestLine("GET /api/v1/users?limit={limit}")
	Page<User> getUsers(@Param long limit);

	@RequestLine("GET /api/v1/users?limit={limit}&cursor={cursor}")
	Page<User> getUsers(@Param long limit, @Param String cursor);

	@RequestLine("POST /api/v1/users/search")
	@Headers(JSON_CONTENT_TYPE)
	Page<User> searchUsers(SearchRequest body);

	@RequestLine("GET /api/v1/users/{id}")
	User getUserById(@Param String id);

	@RequestLine("DELETE /api/v1/users/{id}")
	void deleteUserById(@Param String id);

	@RequestLine("POST /api/v1/users/email/address")
	@Headers(JSON_CONTENT_TYPE)
	User getUserByEmail(AddressRequest body);

	@RequestLine("POST /api/v1/users/wallet/address")
	@Headers(JSON_CONTENT_TYPE)
	User getUserByWallet(AddressRequest body);

	@RequestLine("POST /api/v1/users/phone/number")
	@Headers(JSON_CONTENT_TYPE)
	User getUserByPhone(PhoneRequest body);

	@RequestLine("POST /api/v1/users/twitter/username")
	@Headers(JSON_CONTENT_TYPE)
	User getUserByTwitterUsername(UsernameRequest body);

	@RequestLine("POST /api/v1/users/twitter/subject")
	@Headers(JSON_CONTENT_TYPE)
	User getUserByTwitterSubject(SubjectRequest body);

	@RequestLine("POST /api/v1/users/discord/username")
	@Headers(JSON_CONTENT_TYPE)
	User getUserByDiscordUsername(UsernameRequest body);
	
	@RequestLine("POST /api/v1/users/{id}/custom_metadata")
	@Headers(JSON_CONTENT_TYPE)
	User setCustomMetadata(@Param String id, CustomMetadataUpdateRequest body);

	@RequestLine("GET /api/v1/apps/{applicationId}")
	ApplicationSettings getApplicationSettings(@Param String applicationId);

	static record SearchRequest(
		String searchTerm,
		long limit,
		@JsonInclude(JsonInclude.Include.NON_NULL) @With String cursor
	) {}

	static record AddressRequest(String address) {}

	static record PhoneRequest(String number) {}

	static record UsernameRequest(String username) {}

	static record SubjectRequest(String subject) {}

	static record CustomMetadataUpdateRequest(
		@JsonProperty("custom_metadata") CustomMetadata metadata
	) {}

}