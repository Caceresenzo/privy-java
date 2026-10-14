package dev.caceresenzo.privy.model;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import dev.caceresenzo.privy.util.serial.UnixDateDeserializer;
import io.jsonwebtoken.lang.Collections;
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
	@JsonDeserialize(using = UnixDateDeserializer.AsSeconds.class)
	private Date createdAt;

	@JsonIgnore
	public Optional<LinkedAccount.Wallet> getWallet() {
		return getAccount(LinkedAccount.Wallet.class);
	}

	@JsonIgnore
	public List<LinkedAccount.Wallet> getWallets() {
		return getAccounts(LinkedAccount.Wallet.class);
	}

	@JsonIgnore
	public Optional<LinkedAccount.Email> getEmail() {
		return getAccount(LinkedAccount.Email.class);
	}

	@JsonIgnore
	public Optional<LinkedAccount.Phone> getPhone() {
		return getAccount(LinkedAccount.Phone.class);
	}

	@JsonIgnore
	public Optional<LinkedAccount.Google> getGoogle() {
		return getAccount(LinkedAccount.Google.class);
	}

	@JsonIgnore
	public Optional<LinkedAccount.Twitter> getTwitter() {
		return getAccount(LinkedAccount.Twitter.class);
	}

	@JsonIgnore
	public Optional<LinkedAccount.Discord> getDiscord() {
		return getAccount(LinkedAccount.Discord.class);
	}

	@JsonIgnore
	public Optional<LinkedAccount.Github> getGithub() {
		return getAccount(LinkedAccount.Github.class);
	}

	@JsonIgnore
	public Optional<LinkedAccount.LinkedIn> getLinkedIn() {
		return getAccount(LinkedAccount.LinkedIn.class);
	}

	@JsonIgnore
	public Optional<LinkedAccount.Passkey> getPasskey() {
		return getAccount(LinkedAccount.Passkey.class);
	}

	public <T extends LinkedAccount> Optional<T> getAccount(Class<T> clazz) {
		if (clazz == null) {
			return Optional.empty();
		}

		for (final var account : getLinkedAccounts()) {
			if (clazz.equals(account.getClass())) {
				return Optional.of(clazz.cast(account));
			}
		}

		return Optional.empty();
	}

	public <T extends LinkedAccount> List<T> getAccounts(Class<T> clazz) {
		if (clazz == null) {
			return Collections.emptyList();
		}

		return getLinkedAccounts().stream()
			.filter((account) -> clazz.equals(account.getClass()))
			.map(clazz::cast)
			.toList();
	}

	public CustomMetadata getCustomMetadata() {
		if (customMetadata == null) {
			customMetadata = new CustomMetadata();
		}

		return customMetadata;
	}

}