package dev.caceresenzo.privy.model;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true, defaultImpl = LinkedAccount.Unknown.class)
@JsonSubTypes({
	@JsonSubTypes.Type(value = LinkedAccount.Wallet.class, name = "wallet"),
	@JsonSubTypes.Type(value = LinkedAccount.Email.class, name = "email"),
	@JsonSubTypes.Type(value = LinkedAccount.Phone.class, name = "phone"),
	@JsonSubTypes.Type(value = LinkedAccount.TwitterOAuth.class, name = "twitter_oauth"),
	@JsonSubTypes.Type(value = LinkedAccount.DiscordOAuth.class, name = "discord_oauth"),
})
public sealed interface LinkedAccount {

	@Data
	@ToString(callSuper = false)
	@EqualsAndHashCode(callSuper = true)
	public static final class Wallet extends AccountInfo {

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

	}

	@Data
	@ToString(callSuper = false)
	@EqualsAndHashCode(callSuper = true)
	public static final class Email extends AccountInfo {

		@JsonProperty("address")
		private String address;

	}

	@Data
	@ToString(callSuper = false)
	@EqualsAndHashCode(callSuper = true)
	public static final class Phone extends AccountInfo {

		@JsonProperty("number")
		private String number;

	}

	@Data
	@ToString(callSuper = false)
	@EqualsAndHashCode(callSuper = true)
	public static final class TwitterOAuth extends AccountInfo {

		@JsonProperty("subject")
		private String subject;

		@JsonProperty("username")
		private String username;

		@JsonProperty("name")
		private String name;

		@JsonProperty("profile_picture_url")
		private String profilePictureUrl;

	}

	@Data
	@ToString(callSuper = false)
	@EqualsAndHashCode(callSuper = true)
	public static final class DiscordOAuth extends AccountInfo {

		@JsonProperty("subject")
		private String subject;

		@JsonProperty("username")
		private String username;

		@JsonProperty("email")
		private String email;

	}

	@Data
	public static final class Unknown implements LinkedAccount {

		@JsonProperty("type")
		private String type;

		@JsonAnySetter
		@JsonAnyGetter
		private Map<String, Object> properties;

	}

	@Data
	static sealed class AccountInfo implements LinkedAccount {

		@JsonProperty("verified_at")
		private Date verifiedAt;

		@JsonProperty("first_verified_at")
		private Date firstVerifiedAt;

		@JsonProperty("latest_verified_at")
		private Date latestVerifiedAt;

	}

}