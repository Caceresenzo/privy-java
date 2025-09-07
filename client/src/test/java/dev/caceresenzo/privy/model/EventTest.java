package dev.caceresenzo.privy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.caceresenzo.privy.util.PrivyUtils;
import lombok.SneakyThrows;

class EventTest {

	static ObjectMapper mapper;

	@BeforeAll
	static void setUp() {
		mapper = PrivyUtils.createMapper();
	}

	@Test
	void test() {
		final var event = read("""
			{
				"type": "privy.test",
				"message": "Hello, World!"
			}
			""");

		final var test = assertInstanceOf(Event.Test.class, event);

		assertEquals("Hello, World!", test.getMessage());
	}

	@Test
	void userCreated() {
		final var event = read("""
			{
				"type": "user.created",
				"user": {
					"created_at": 969628260,
					"has_accepted_terms": false,
					"id": "did:privy:cfbsvtqo2c22202mo08847jdux2z",
					"is_guest": false,
					"linked_accounts": [
						{
							"address": "bilbo@privy.io",
							"first_verified_at": 969628260,
							"latest_verified_at": 969628260,
							"type": "email",
							"verified_at": 969628260
						}
					],
					"mfa_methods": []
				}
			}
			""");

		final var userCreated = assertInstanceOf(Event.UserCreated.class, event);

		assertEquals("did:privy:cfbsvtqo2c22202mo08847jdux2z", userCreated.getUser().getId());
	}

	@Test
	void userAuthenticated() {
		final var event = read("""
			{
				"type": "user.authenticated",
				"account": {
					"address": "bilbo@privy.io",
					"first_verified_at": 969628260,
					"latest_verified_at": 969628260,
					"type": "email",
					"verified_at": 969628260
				},
				"user": {
					"created_at": 969628260,
					"has_accepted_terms": false,
					"id": "did:privy:cfbsvtqo2c22202mo08847jdux2z",
					"is_guest": false,
					"linked_accounts": [
						{
							"address": "bilbo@privy.io",
							"first_verified_at": 969628260,
							"latest_verified_at": 969628260,
							"type": "email",
							"verified_at": 969628260
						}
					],
					"mfa_methods": []
				}
			}
			""");

		final var userAuthenticated = assertInstanceOf(Event.UserAuthenticated.class, event);

		assertInstanceOf(LinkedAccount.Email.class, userAuthenticated.getAccount());
		assertEquals("did:privy:cfbsvtqo2c22202mo08847jdux2z", userAuthenticated.getUser().getId());
	}

	@Test
	void userLinkedAccount() {
		final var event = read("""
			{
				"type": "user.linked_account",
				"account": {
					"address": "bilbo@privy.io",
					"first_verified_at": 969628260,
					"latest_verified_at": 969628260,
					"type": "email",
					"verified_at": 969628260
				},
				"user": {
					"created_at": 969628260,
					"has_accepted_terms": false,
					"id": "did:privy:cfbsvtqo2c22202mo08847jdux2z",
					"is_guest": false,
					"linked_accounts": [
						{
							"address": "bilbo@privy.io",
							"first_verified_at": 969628260,
							"latest_verified_at": 969628260,
							"type": "email",
							"verified_at": 969628260
						}
					],
					"mfa_methods": []
				}
			}
			""");

		final var userLinkedAccount = assertInstanceOf(Event.UserLinkedAccount.class, event);

		assertInstanceOf(LinkedAccount.Email.class, userLinkedAccount.getAccount());
		assertEquals("did:privy:cfbsvtqo2c22202mo08847jdux2z", userLinkedAccount.getUser().getId());
	}

	@Test
	void userUnlinkedAccount() {
		final var event = read("""
			{
				"type": "user.unlinked_account",
				"account": {
					"address": "bilbo@privy.io",
					"first_verified_at": 969628260,
					"latest_verified_at": 969628260,
					"type": "email",
					"verified_at": 969628260
				},
				"user": {
					"created_at": 969628260,
					"has_accepted_terms": false,
					"id": "did:privy:cfbsvtqo2c22202mo08847jdux2z",
					"is_guest": false,
					"linked_accounts": [
						{
							"address": "bilbo@privy.io",
							"first_verified_at": 969628260,
							"latest_verified_at": 969628260,
							"type": "email",
							"verified_at": 969628260
						}
					],
					"mfa_methods": []
				}
			}
			""");

		final var userUnlinkedAccount = assertInstanceOf(Event.UserUnlinkedAccount.class, event);

		assertInstanceOf(LinkedAccount.Email.class, userUnlinkedAccount.getAccount());
		assertEquals("did:privy:cfbsvtqo2c22202mo08847jdux2z", userUnlinkedAccount.getUser().getId());
	}

	@Test
	void userUpdatedAccount() {
		final var event = read("""
			{
				"type": "user.updated_account",
				"account": {
					"address": "bilbo@privy.io",
					"first_verified_at": 969628260,
					"latest_verified_at": 969628260,
					"type": "email",
					"verified_at": 969628260
				},
				"user": {
					"created_at": 969628260,
					"has_accepted_terms": false,
					"id": "did:privy:cfbsvtqo2c22202mo08847jdux2z",
					"is_guest": false,
					"linked_accounts": [
						{
							"address": "bilbo@privy.io",
							"first_verified_at": 969628260,
							"latest_verified_at": 969628260,
							"type": "email",
							"verified_at": 969628260
						}
					],
					"mfa_methods": []
				}
			}
			""");

		final var userUpdatedAccount = assertInstanceOf(Event.UserUpdatedAccount.class, event);

		assertInstanceOf(LinkedAccount.Email.class, userUpdatedAccount.getAccount());
		assertEquals("did:privy:cfbsvtqo2c22202mo08847jdux2z", userUpdatedAccount.getUser().getId());
	}

	@Test
	void userTransferredAccount() {
		final var event = read("""
			{
			    "type": "user.transferred_account",
			    "fromUser": {
			        "id": "did:privy:clu2wsin402h9h9kt6ae7dfuh"
			    },
			    "toUser": {
			        "created_at": 969628260,
			        "has_accepted_terms": false,
			        "id": "did:privy:cfbsvtqo2c22202mo08847jdux2z",
			        "is_guest": false,
			        "linked_accounts": [{
			                "address": "bilbo@privy.io",
			                "first_verified_at": 969628260,
			                "latest_verified_at": 969628260,
			                "type": "email",
			                "verified_at": 969628260
			            }, {
			                "address": "+1234567890",
			                "first_verified_at": 969628260,
			                "latest_verified_at": 969628260,
			                "type": "phone",
			                "verified_at": 969628260
			            }
			        ]
			    },
			    "account": {
			        "address": "+1234567890",
			        "first_verified_at": 969628260,
			        "latest_verified_at": 969628260,
			        "type": "phone",
			        "verified_at": 969628260
			    },
			    "deletedUser": true
			}
			""");

		final var userTransferredAccount = assertInstanceOf(Event.UserTransferredAccount.class, event);

		assertInstanceOf(LinkedAccount.Phone.class, userTransferredAccount.getAccount());
		assertEquals("did:privy:clu2wsin402h9h9kt6ae7dfuh", userTransferredAccount.getFromUserId());
		assertEquals("did:privy:cfbsvtqo2c22202mo08847jdux2z", userTransferredAccount.getToUserId());
		assertTrue(userTransferredAccount.isDeleted());
	}

	@Test
	void multiFactorAuthenticationEnabled() {
		final var event = read("""
			{
				"type": "mfa.enabled",
				"user_id": "user_123",
				"method": "sms"
			}
			""");

		final var multiFactorAuthenticationEnabled = assertInstanceOf(Event.MultiFactorAuthenticationEnabled.class, event);

		assertEquals("user_123", multiFactorAuthenticationEnabled.getUserId());
		assertEquals("sms", multiFactorAuthenticationEnabled.getMethod());
	}

	@Test
	void multiFactorAuthenticationDisabled() {
		final var event = read("""
			{
				"type": "mfa.disabled",
				"user_id": "user_123",
				"method": "sms"
			}
			""");

		final var multiFactorAuthenticationDisabled = assertInstanceOf(Event.MultiFactorAuthenticationDisabled.class, event);

		assertEquals("user_123", multiFactorAuthenticationDisabled.getUserId());
		assertEquals("sms", multiFactorAuthenticationDisabled.getMethod());
	}

	@SneakyThrows
	private Event read(String json) {
		return mapper.readValue(json, Event.class);
	}

	@AfterAll
	static void tearDown() {
		mapper = null;
	}

}