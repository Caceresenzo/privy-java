package dev.caceresenzo.privy.model;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true, defaultImpl = LinkedAccount.Unknown.class)
@JsonSubTypes({
	@JsonSubTypes.Type(value = LinkedAccount.Wallet.class, name = "wallet"),
	@JsonSubTypes.Type(value = LinkedAccount.Email.class, name = "email"),
	@JsonSubTypes.Type(value = LinkedAccount.Phone.class, name = "phone"),
	@JsonSubTypes.Type(value = LinkedAccount.DiscordOAuth.class, name = "discord_oauth"),
})
public sealed interface LinkedAccount {

	@Data
	public static final class Wallet implements LinkedAccount {

		@JsonProperty("address")
		private String address;

		@JsonProperty("imported")
		private boolean imported;

		@JsonProperty("wallet_index")
		private int walletIndex;

		@JsonProperty("chain_id")
		private String chainId;

		@JsonProperty("chain_type")
		private String chainType;

		@JsonProperty("wallet_client")
		private String walletClient;

		@JsonProperty("wallet_client_type")
		private String walletClientType;

		@JsonProperty("connector_type")
		private String connectorType;

		@JsonProperty("recovery_method")
		private String recoveryMethod;

		@JsonProperty("verified_at")
		private Date verifiedAt;

		@JsonProperty("first_verified_at")
		private Date firstVerifiedAt;

		@JsonProperty("latest_verified_at")
		private Date latestVerifiedAt;

	}

	@Data
	public static final class Email implements LinkedAccount {

		@JsonProperty("address")
		private String address;

		@JsonProperty("verified_at")
		private Date verifiedAt;

		@JsonProperty("first_verified_at")
		private Date firstVerifiedAt;

		@JsonProperty("latest_verified_at")
		private Date latestVerifiedAt;

	}

	@Data
	public static final class Phone implements LinkedAccount {

		@JsonProperty("number")
		private String number;

		@JsonProperty("verified_at")
		private Date verifiedAt;

		@JsonProperty("first_verified_at")
		private Date firstVerifiedAt;

		@JsonProperty("latest_verified_at")
		private Date latestVerifiedAt;

	}

	@Data
	public static final class DiscordOAuth implements LinkedAccount {

		@JsonProperty("subject")
		private String subject;

		@JsonProperty("username")
		private String username;

		@JsonProperty("email")
		private String email;

		@JsonProperty("verified_at")
		private Date verifiedAt;

		@JsonProperty("first_verified_at")
		private Date firstVerifiedAt;

		@JsonProperty("latest_verified_at")
		private Date latestVerifiedAt;

	}

	@Data
	public static final class Unknown implements LinkedAccount {

		@JsonProperty("type")
		private String type;

		@JsonAnySetter
		@JsonAnyGetter
		private Map<String, Object> properties;

	}

}