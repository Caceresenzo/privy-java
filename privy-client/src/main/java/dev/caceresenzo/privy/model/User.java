package dev.caceresenzo.privy.model;

import java.util.Date;
import java.util.List;

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

	@JsonProperty("created_at")
	private Date createdAt;

	public LinkedAccount.Wallet getWallet() {
		return getAccount(LinkedAccount.Wallet.class);
	}

	public LinkedAccount.Email getEmail() {
		return getAccount(LinkedAccount.Email.class);
	}

	public LinkedAccount.Phone getPhone() {
		return getAccount(LinkedAccount.Phone.class);
	}

	public LinkedAccount.Twitter getTwitter() {
		return getAccount(LinkedAccount.Twitter.class);
	}

	public LinkedAccount.Discord getDiscord() {
		return getAccount(LinkedAccount.Discord.class);
	}

	@SuppressWarnings("unchecked")
	public <T extends LinkedAccount> T getAccount(Class<T> clazz) {
		for (final var account : getLinkedAccounts()) {

			if (account.getClass().isAssignableFrom(clazz)) {
				return (T) account;
			}
		}

		return null;
	}

}