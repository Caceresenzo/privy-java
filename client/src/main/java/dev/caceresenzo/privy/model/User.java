package dev.caceresenzo.privy.model;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class User {

	@JsonProperty("id")
	private String id;

	@JsonProperty("linked_accounts")
	private List<LinkedAccount> linkedAccounts;

	@JsonProperty("has_accepted_terms")
	public boolean hasAcceptedTerms;

	@JsonProperty("is_guest")
	public boolean isGuest;

	@JsonProperty("custom_metadata")
	public CustomMetadata customMetadata;

	@JsonProperty("created_at")
	private Date createdAt;

	public Optional<LinkedAccount.Wallet> getWallet() {
		return getAccount(LinkedAccount.Wallet.class);
	}

	public Optional<LinkedAccount.Email> getEmail() {
		return getAccount(LinkedAccount.Email.class);
	}

	public Optional<LinkedAccount.Phone> getPhone() {
		return getAccount(LinkedAccount.Phone.class);
	}

	public Optional<LinkedAccount.Google> getGoogle() {
		return getAccount(LinkedAccount.Google.class);
	}

	public Optional<LinkedAccount.Twitter> getTwitter() {
		return getAccount(LinkedAccount.Twitter.class);
	}

	public Optional<LinkedAccount.Discord> getDiscord() {
		return getAccount(LinkedAccount.Discord.class);
	}

	public Optional<LinkedAccount.Github> getGithub() {
		return getAccount(LinkedAccount.Github.class);
	}

	@SuppressWarnings("unchecked")
	public <T extends LinkedAccount> Optional<T> getAccount(Class<T> clazz) {
		for (final var account : getLinkedAccounts()) {

			if (account.getClass().isAssignableFrom(clazz)) {
				return Optional.of((T) account);
			}
		}

		return Optional.empty();
	}

}