package dev.caceresenzo.privy.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.Date;
import java.util.Map;

import org.junit.jupiter.api.Test;

import dev.caceresenzo.privy.util.PrivyMapper;
import lombok.SneakyThrows;

@SuppressWarnings("deprecation")
class LinkedAccountTest {

	@Test
	void wallet() {
		final var linkedAccount = read("""
			{
				"type": "wallet",
				"address": "0x0123456789abcdef0123456789abcdef01234567",
				"imported": false,
				"wallet_index": 0,
				"chain_id": "eip155:1",
				"chain_type": "ethereum",
				"wallet_client": "privy",
				"wallet_client_type": "privy",
				"connector_type": "embedded",
				"verified_at": 1724800479,
				"first_verified_at": 1724800479,
				"latest_verified_at": 1724800479,
				"recovery_method": "privy"
			}
			""");

		final var wallet = assertInstanceOf(LinkedAccount.Wallet.class, linkedAccount);

		assertEquals("0x0123456789abcdef0123456789abcdef01234567", wallet.getAddress());
		assertFalse(wallet.isImported());
		assertEquals(0, wallet.getHdWalletIndex());
		assertEquals("eip155:1", wallet.getChainId());
		assertEquals("ethereum", wallet.getChainType());
		assertEquals("privy", wallet.getWalletClient());
		assertEquals("privy", wallet.getWalletClientType());
		assertEquals("embedded", wallet.getConnectorType());

		final var date = new Date(1724800479l * 1000);
		assertEquals(date, wallet.getVerifiedAt());
		assertEquals(date, wallet.getFirstVerifiedAt());
		assertEquals(date, wallet.getLatestVerifiedAt());
	}

	@Test
	void email() {
		final var linkedAccount = read("""
			{
				"type": "email",
				"address": "johndoe@example.com",
				"verified_at": 1724800476,
				"first_verified_at": 1724800476,
				"latest_verified_at": 1725126079
			}
			""");

		final var email = assertInstanceOf(LinkedAccount.Email.class, linkedAccount);

		assertEquals("johndoe@example.com", email.getAddress());

		final var date = new Date(1724800476l * 1000);
		assertEquals(date, email.getVerifiedAt());
		assertEquals(date, email.getFirstVerifiedAt());
		assertEquals(new Date(1725126079l * 1000), email.getLatestVerifiedAt());
	}

	@Test
	void phone() {
		final var linkedAccount = read("""
			{
				"type": "phone",
				"number": "+12819374192",
				"verified_at": 1725378363,
				"first_verified_at": 1725378363,
				"latest_verified_at": 1725378363
			}
			""");

		final var phone = assertInstanceOf(LinkedAccount.Phone.class, linkedAccount);

		assertEquals("+12819374192", phone.getNumber());

		final var date = new Date(1725378363l * 1000);
		assertEquals(date, phone.getVerifiedAt());
		assertEquals(date, phone.getFirstVerifiedAt());
		assertEquals(date, phone.getLatestVerifiedAt());
	}

	@Test
	void google() {
		final var linkedAccount = read("""
			{
				"type": "google_oauth",
				"subject": "123456789012345678901",
				"email": "johndoe@example.com",
				"name": "John Doe",
				"verified_at": 1725376993,
				"first_verified_at": 1725376993,
				"latest_verified_at": 1725376993
			}
			""");

		final var google = assertInstanceOf(LinkedAccount.Google.class, linkedAccount);

		assertEquals("123456789012345678901", google.getSubject());
		assertEquals("johndoe@example.com", google.getEmail());
		assertEquals("John Doe", google.getName());

		final var date = new Date(1725376993l * 1000);
		assertEquals(date, google.getVerifiedAt());
		assertEquals(date, google.getFirstVerifiedAt());
		assertEquals(date, google.getLatestVerifiedAt());
	}

	@Test
	void twitter() {
		final var linkedAccount = read("""
			{
				"type": "twitter_oauth",
				"subject": "123456789",
				"name": "John Doe",
				"username": "johndoe",
				"profile_picture_url": "https://pbs.twimg.com/profile_images/x/x.jpg",
				"verified_at": 1725376993,
				"first_verified_at": 1725376993,
				"latest_verified_at": 1725376993
			}
			""");

		final var twitter = assertInstanceOf(LinkedAccount.Twitter.class, linkedAccount);

		assertEquals("123456789", twitter.getSubject());
		assertEquals("John Doe", twitter.getName());
		assertEquals("johndoe", twitter.getUsername());
		assertEquals("https://pbs.twimg.com/profile_images/x/x.jpg", twitter.getProfilePictureUrl());

		final var date = new Date(1725376993l * 1000);
		assertEquals(date, twitter.getVerifiedAt());
		assertEquals(date, twitter.getFirstVerifiedAt());
		assertEquals(date, twitter.getLatestVerifiedAt());
	}

	@Test
	void discord() {
		final var linkedAccount = read("""
			{
				"type": "discord_oauth",
				"subject": "123456789",
				"username": "johndoe#0",
				"email": "johndoe@example.com",
				"verified_at": 1725127202,
				"first_verified_at": 1725127202,
				"latest_verified_at": 1725127202
			}
			""");

		final var discord = assertInstanceOf(LinkedAccount.Discord.class, linkedAccount);

		assertEquals("123456789", discord.getSubject());
		assertEquals("johndoe#0", discord.getUsername());
		assertEquals("johndoe@example.com", discord.getEmail());

		final var date = new Date(1725127202l * 1000);
		assertEquals(date, discord.getVerifiedAt());
		assertEquals(date, discord.getFirstVerifiedAt());
		assertEquals(date, discord.getLatestVerifiedAt());
	}

	@Test
	void github() {
		final var linkedAccount = read("""
			{
				"type": "github_oauth",
				"subject": "1234567",
				"username": "johndoe",
				"email": "johndoe@example.com",
				"name": "John Doe",
				"verified_at": 1725376993,
				"first_verified_at": 1725376993,
				"latest_verified_at": 1725376993
			}
			""");

		final var github = assertInstanceOf(LinkedAccount.Github.class, linkedAccount);

		assertEquals("1234567", github.getSubject());
		assertEquals("johndoe", github.getUsername());
		assertEquals("johndoe@example.com", github.getEmail());
		assertEquals("John Doe", github.getName());

		final var date = new Date(1725376993l * 1000);
		assertEquals(date, github.getVerifiedAt());
		assertEquals(date, github.getFirstVerifiedAt());
		assertEquals(date, github.getLatestVerifiedAt());
	}

	@Test
	void linkedIn() {
		final var linkedAccount = read("""
			{
				"type": "linkedin_oauth",
				"subject": "1234567",
				"email": "johndoe@example.com",
				"name": "John Doe",
				"verified_at": 1725376993,
				"first_verified_at": 1725376993,
				"latest_verified_at": 1725376993
			}
			""");

		final var linkedIn = assertInstanceOf(LinkedAccount.LinkedIn.class, linkedAccount);

		assertEquals("1234567", linkedIn.getSubject());
		assertEquals("johndoe@example.com", linkedIn.getEmail());
		assertEquals("John Doe", linkedIn.getName());

		final var date = new Date(1725376993l * 1000);
		assertEquals(date, linkedIn.getVerifiedAt());
		assertEquals(date, linkedIn.getFirstVerifiedAt());
		assertEquals(date, linkedIn.getLatestVerifiedAt());
	}

	@Test
	void passkey() {
		final var linkedAccount = read("""
			{
				"type": "passkey",
				"credential_id": "aabbccddeeff",
				"verified_at": 1725376993,
				"first_verified_at": 1725376993,
				"latest_verified_at": 1725376993
			}
			""");

		final var passkey = assertInstanceOf(LinkedAccount.Passkey.class, linkedAccount);

		assertEquals("aabbccddeeff", passkey.getCredentialId());

		final var date = new Date(1725376993l * 1000);
		assertEquals(date, passkey.getVerifiedAt());
		assertEquals(date, passkey.getFirstVerifiedAt());
		assertEquals(date, passkey.getLatestVerifiedAt());
	}

	@Test
	void other() {
		final var linkedAccount = read("""
			{
				"type": "unsupported",
				"subject": "123456789",
				"name": "John Doe",
				"verified_at": 1725376993
			}
			""");

		final var other = assertInstanceOf(LinkedAccount.Other.class, linkedAccount);

		assertEquals("unsupported", other.getType());
		assertThat(other.getProperties()).containsExactly(
			Map.entry("subject", "123456789"),
			Map.entry("name", "John Doe")
		);

		assertEquals(new Date(1725376993l * 1000), other.getVerifiedAt());
	}

	@SneakyThrows
	private LinkedAccount read(String json) {
		return PrivyMapper.INSTANCE.readValue(json, LinkedAccount.class);
	}

}