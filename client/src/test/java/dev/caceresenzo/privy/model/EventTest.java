package dev.caceresenzo.privy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dev.caceresenzo.privy.util.PrivyMapper;
import lombok.SneakyThrows;

class EventTest {

	@Test
	void test() {
		final var receivedEvent = read("""
			{
				"type": "privy.test",
				"message": "Hello, World!"
			}
			""");

		final var event = assertInstanceOf(Event.Test.class, receivedEvent);

		assertEquals("Hello, World!", event.getMessage());
	}

	@Test
	void userCreated() {
		final var receivedEvent = read("""
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

		final var event = assertInstanceOf(Event.UserCreated.class, receivedEvent);

		assertEquals("did:privy:cfbsvtqo2c22202mo08847jdux2z", event.getUser().getId());
	}

	@Test
	void userAuthenticated() {
		final var receivedEvent = read("""
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

		final var event = assertInstanceOf(Event.UserAuthenticated.class, receivedEvent);

		assertInstanceOf(LinkedAccount.Email.class, event.getAccount());
		assertEquals("did:privy:cfbsvtqo2c22202mo08847jdux2z", event.getUser().getId());
	}

	@Test
	void userLinkedAccount() {
		final var receivedEvent = read("""
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

		final var event = assertInstanceOf(Event.UserLinkedAccount.class, receivedEvent);

		assertInstanceOf(LinkedAccount.Email.class, event.getAccount());
		assertEquals("did:privy:cfbsvtqo2c22202mo08847jdux2z", event.getUser().getId());
	}

	@Test
	void userUnlinkedAccount() {
		final var receivedEvent = read("""
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

		final var event = assertInstanceOf(Event.UserUnlinkedAccount.class, receivedEvent);

		assertInstanceOf(LinkedAccount.Email.class, event.getAccount());
		assertEquals("did:privy:cfbsvtqo2c22202mo08847jdux2z", event.getUser().getId());
	}

	@Test
	void userUpdatedAccount() {
		final var receivedEvent = read("""
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

		final var event = assertInstanceOf(Event.UserUpdatedAccount.class, receivedEvent);

		assertInstanceOf(LinkedAccount.Email.class, event.getAccount());
		assertEquals("did:privy:cfbsvtqo2c22202mo08847jdux2z", event.getUser().getId());
	}

	@Test
	void userTransferredAccount() {
		final var receivedEvent = read("""
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

		final var event = assertInstanceOf(Event.UserTransferredAccount.class, receivedEvent);

		assertInstanceOf(LinkedAccount.Phone.class, event.getAccount());
		assertEquals("did:privy:clu2wsin402h9h9kt6ae7dfuh", event.getFromUserId());
		assertEquals("did:privy:cfbsvtqo2c22202mo08847jdux2z", event.getToUserId());
		assertTrue(event.isDeleted());
	}

	@Test
	void userWalletCreated() {
		final var receivedEvent = read("""
			{
			    "type": "user.wallet_created",
			    "user": {
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
			            }
			        ],
			        "mfa_methods": []
			    },
			    "wallet": {
			        "type": "wallet",
			        "address": "0x123...",
			        "chain_type": "ethereum"
			    }
			}
			""");

		final var event = assertInstanceOf(Event.UserWalletCreated.class, receivedEvent);

		assertEquals("did:privy:cfbsvtqo2c22202mo08847jdux2z", event.getUserId());
		assertEquals("0x123...", event.getWallet().getAddress());
	}

	@Test
	void multiFactorAuthenticationEnabled() {
		final var receivedEvent = read("""
			{
				"type": "mfa.enabled",
				"user_id": "user_123",
				"method": "sms"
			}
			""");

		final var event = assertInstanceOf(Event.MultiFactorAuthenticationEnabled.class, receivedEvent);

		assertEquals("user_123", event.getUserId());
		assertEquals("sms", event.getMethod());
	}

	@Test
	void multiFactorAuthenticationDisabled() {
		final var receivedEvent = read("""
			{
				"type": "mfa.disabled",
				"user_id": "user_123",
				"method": "sms"
			}
			""");

		final var event = assertInstanceOf(Event.MultiFactorAuthenticationDisabled.class, receivedEvent);

		assertEquals("user_123", event.getUserId());
		assertEquals("sms", event.getMethod());
	}

	@Test
	void privateKeyExported() {
		final var receivedEvent = read("""
			{
				"type": "wallet.private_key_export",
				"user_id": "user_123",
				"wallet_id": "wallet_123",
				"wallet_address": "0x123..."
			}
			""");

		final var event = assertInstanceOf(Event.PrivateKeyExported.class, receivedEvent);

		assertEquals("user_123", event.getUserId());
		assertEquals("wallet_123", event.getWalletId());
		assertEquals("0x123...", event.getWalletAddress());
	}

	@Test
	void walletRecoverySetup() {
		final var receivedEvent = read("""
			{
				"type": "wallet.recovery_setup",
				"user_id": "user_123",
				"wallet_id": "wallet_123",
				"wallet_address": "0x123...",
				"method": "passkey"
			}
			""");

		final var event = assertInstanceOf(Event.WalletRecoverySetup.class, receivedEvent);

		assertEquals("user_123", event.getUserId());
		assertEquals("wallet_123", event.getWalletId());
		assertEquals("0x123...", event.getWalletAddress());
		assertEquals("passkey", event.getMethod());
	}

	@Test
	void walletRecovered() {
		final var receivedEvent = read("""
			{
				"type": "wallet.recovered",
				"user_id": "user_123",
				"wallet_id": "wallet_123",
				"wallet_address": "0x123..."
			}
			""");

		final var event = assertInstanceOf(Event.WalletRecovered.class, receivedEvent);

		assertEquals("user_123", event.getUserId());
		assertEquals("wallet_123", event.getWalletId());
		assertEquals("0x123...", event.getWalletAddress());
	}

	@SneakyThrows
	private Event read(String json) {
		return PrivyMapper.INSTANCE.readValue(json, Event.class);
	}

}