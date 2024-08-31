package dev.caceresenzo.privy.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true, defaultImpl = LinkedAccount.Unknown.class)
@JsonSubTypes({
	@JsonSubTypes.Type(value = LinkedAccount.Email.class, name = "email"),
	@JsonSubTypes.Type(value = LinkedAccount.Wallet.class, name = "wallet"),
	@JsonSubTypes.Type(value = LinkedAccount.Phone.class, name = "phone"),
	@JsonSubTypes.Type(value = LinkedAccount.DiscordOAuth.class, name = "discord_oauth"),
})
@Data
public class LinkedAccount {

	@JsonProperty("address")
	protected String address;

	@JsonProperty("verified_at")
	protected Date verifiedAt;

	@JsonProperty("first_verified_at")
	protected Date firstVerifiedAt;

	@JsonProperty("latest_verified_at")
	protected Date latestVerifiedAt;

	@Data
	@ToString(callSuper = true)
	@EqualsAndHashCode(callSuper = true)
	public static final class Email extends LinkedAccount {}

	@Data
	@ToString(callSuper = true)
	@EqualsAndHashCode(callSuper = true)
	public static final class Wallet extends LinkedAccount {

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

	}

	@Data
	@ToString(callSuper = true)
	@EqualsAndHashCode(callSuper = true)
	public static final class Phone extends LinkedAccount {

		@JsonProperty("number")
		private String number;

	}

	@Data
	@ToString(callSuper = true)
	@EqualsAndHashCode(callSuper = true)
	public static final class DiscordOAuth extends LinkedAccount {

		@JsonProperty("subject")
		private String subject;

		@JsonProperty("username")
		private String username;

		@JsonProperty("email")
		private String email;

	}

	@Data
	@ToString(callSuper = true)
	@EqualsAndHashCode(callSuper = true)
	public static final class Unknown extends LinkedAccount {

		@JsonProperty("type")
		private String type;

	}

}