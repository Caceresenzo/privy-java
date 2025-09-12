package dev.caceresenzo.privy.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

class UserTest {

	private static final LinkedAccount.Wallet WALLET = new LinkedAccount.Wallet();
	private static final LinkedAccount.Email EMAIL = new LinkedAccount.Email();
	private static final LinkedAccount.Google GOOGLE = new LinkedAccount.Google();
	private static final LinkedAccount.Twitter TWITTER = new LinkedAccount.Twitter();
	private static final LinkedAccount.Discord DISCORD = new LinkedAccount.Discord();
	private static final LinkedAccount.Github GITHUB = new LinkedAccount.Github();
	private static final LinkedAccount.LinkedIn LINKEDIN = new LinkedAccount.LinkedIn();
	private static final LinkedAccount.Passkey PASSKEY = new LinkedAccount.Passkey();

	private static final User USER = new User();

	static {
		USER.setLinkedAccounts(List.of(
			WALLET,
			EMAIL,
			GOOGLE,
			TWITTER,
			DISCORD,
			GITHUB,
			LINKEDIN,
			PASSKEY
		));
	}

	@Test
	void getWallet() {
		assertThat(USER.getWallet())
			.contains(WALLET);
	}

	@Test
	void getEmail() {
		assertThat(USER.getEmail())
			.contains(EMAIL);
	}

	@Test
	void getPhone() {
		assertThat(USER.getPhone())
			.isEmpty();
	}

	@Test
	void getGoogle() {
		assertThat(USER.getGoogle())
			.contains(GOOGLE);
	}

	@Test
	void getTwitter() {
		assertThat(USER.getTwitter())
			.contains(TWITTER);
	}

	@Test
	void getDiscord() {
		assertThat(USER.getDiscord())
			.contains(DISCORD);
	}

	@Test
	void getGithub() {
		assertThat(USER.getGithub())
			.contains(GITHUB);
	}

	@Test
	void getLinkedIn() {
		assertThat(USER.getLinkedIn())
			.contains(LINKEDIN);
	}

	@Test
	void getPasskey() {
		assertThat(USER.getPasskey())
			.contains(PASSKEY);
	}

	@Test
	void getAccount() {
		assertThat(USER.getAccount(LinkedAccount.Wallet.class))
			.contains(WALLET);

		assertThat(USER.getAccount(LinkedAccount.Email.class))
			.contains(EMAIL);

		assertThat(USER.getAccount(LinkedAccount.Phone.class))
			.isEmpty();

		assertThat(USER.getAccount(LinkedAccount.Google.class))
			.contains(GOOGLE);

		assertThat(USER.getAccount(LinkedAccount.Twitter.class))
			.contains(TWITTER);

		assertThat(USER.getAccount(LinkedAccount.Discord.class))
			.contains(DISCORD);

		assertThat(USER.getAccount(LinkedAccount.Github.class))
			.contains(GITHUB);

		assertThat(USER.getAccount(LinkedAccount.LinkedIn.class))
			.contains(LINKEDIN);

		assertThat(USER.getAccount(LinkedAccount.Passkey.class))
			.contains(PASSKEY);

		assertThat(USER.getAccount(null))
			.isEmpty();
	}

	@Test
	void getCustomMetadata() {
		final var user = new User();
		assertNotNull(user.getCustomMetadata());

		user.setCustomMetadata(null);
		assertNotNull(user.getCustomMetadata());
	}

}