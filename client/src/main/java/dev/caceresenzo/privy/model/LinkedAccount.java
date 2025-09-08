package dev.caceresenzo.privy.model;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true, defaultImpl = LinkedAccount.Other.class)
@JsonSubTypes({
	@JsonSubTypes.Type(value = LinkedAccount.Wallet.class, name = "wallet"),
	@JsonSubTypes.Type(value = LinkedAccount.Email.class, name = "email"),
	@JsonSubTypes.Type(value = LinkedAccount.Phone.class, name = "phone"),
	@JsonSubTypes.Type(value = LinkedAccount.Google.class, name = "google_oauth"),
	@JsonSubTypes.Type(value = LinkedAccount.Twitter.class, name = "twitter_oauth"),
	@JsonSubTypes.Type(value = LinkedAccount.Discord.class, name = "discord_oauth"),
	@JsonSubTypes.Type(value = LinkedAccount.Github.class, name = "github_oauth"),
})
@Data
public abstract sealed class LinkedAccount {

	@JsonProperty("verified_at")
	private Date verifiedAt;

	@JsonProperty("first_verified_at")
	private Date firstVerifiedAt;

	@JsonProperty("latest_verified_at")
	@JsonAlias("lv")
	private Date latestVerifiedAt;

	/** Object representation of a user's wallet. */
	@Data
	@ToString(callSuper = false)
	@EqualsAndHashCode(callSuper = true)
	public static final class Wallet extends LinkedAccount {

		/** The wallet address. */
		@JsonProperty("address")
		private String address;

		/**
		 * Chain type of the wallet address.
		 *
		 * @deprecated Use `chainId` instead.
		 */
		@Deprecated
		@JsonProperty("chain_type")
		private String chainType;

		/**
		 * CAIP-2 formatted chain ID during the most recent verification.
		 *
		 * e.g. eip155:1, eip155:5, eip155:137, etc.
		 */
		@JsonProperty("chain_id")
		private String chainId;

		/**
		 * The wallet client used for this wallet during the most recent verification.
		 *
		 * If the value is `privy`, then this is a privy embedded wallet.
		 *
		 * Other values include but are not limited to `metamask`, `rainbow`, `coinbase_wallet`, etc.
		 */
		@JsonProperty("wallet_client_type")
		private String walletClientType;

		/**
		 * The connector type used for this wallet during the most recent verification.
		 *
		 * This includes but is not limited to `injected`, `wallet_connect`, `coinbase_wallet`, `embedded`.
		 */
		@JsonProperty("connector_type")
		private String connectorType;

		/**
		 * The index of this wallet for an HD wallet, if the wallet is an embedded wallet.
		 */
		@JsonProperty("wallet_index")
		private int hdWalletIndex;

		/**
		 * Whether or not this wallet was imported by the user. Will be <code>null</code> if the wallet is not an embedded wallet.
		 */
		@JsonProperty("imported")
		private Boolean imported;

		@JsonProperty("wallet_client")
		private String walletClient;

		@JsonProperty("recovery_method")
		private String recoveryMethod;

		@JsonIgnore
		public boolean isImported() {
			return Boolean.TRUE.equals(imported);
		}

	}

	/** Object representation of a user's email. */
	@Data
	@ToString(callSuper = false)
	@EqualsAndHashCode(callSuper = true)
	public static final class Email extends LinkedAccount {

		/** The email address. */
		@JsonProperty("address")
		private String address;

	}

	/** Object representation of a user's phone number. */
	@Data
	@ToString(callSuper = false)
	@EqualsAndHashCode(callSuper = true)
	public static final class Phone extends LinkedAccount {

		/** The phone number. */
		@JsonProperty("number")
		private String number;

	}

	/** Object representation of a user's Google account. */
	@Data
	@ToString(callSuper = false)
	@EqualsAndHashCode(callSuper = true)
	public static final class Google extends LinkedAccount {

		/** Object representation of a user's Google account. */
		@JsonProperty("subject")
		private String subject;

		/** The email associated with the Google account. */
		@JsonProperty("email")
		private String email;

		/** The name associated with the Google account. */
		@JsonProperty("name")
		private String name;

	}

	/** Object representation of a user's Twitter account. */
	@Data
	@ToString(callSuper = false)
	@EqualsAndHashCode(callSuper = true)
	public static final class Twitter extends LinkedAccount {

		/** The `sub` claim from the Twitter-issued JWT for this account. */
		@JsonProperty("subject")
		private String subject;

		/** The username associated with the Twitter account. */
		@JsonProperty("username")
		private String username;

		/** The name associated with the Twitter account. */
		@JsonProperty("name")
		private String name;

		/** The profile picture URL associated with the Twitter account. */
		@JsonProperty("profile_picture_url")
		private String profilePictureUrl;

	}

	/** Object representation of a user's Discord account. */
	@Data
	@ToString(callSuper = false)
	@EqualsAndHashCode(callSuper = true)
	public static final class Discord extends LinkedAccount {

		/** The `sub` claim from the Discord-issued JWT for this account. */
		@JsonProperty("subject")
		private String subject;

		/** The username associated with the Discord account.  */
		@JsonProperty("username")
		private String username;

		/** The email associated with the Discord account. */
		@JsonProperty("email")
		private String email;

	}

	/** Object representation of a user's Github account. */
	@Data
	@ToString(callSuper = false)
	@EqualsAndHashCode(callSuper = true)
	public static final class Github extends LinkedAccount {

		/** The `sub` claim from the Github-issued JWT for this account. */
		@JsonProperty("subject")
		private String subject;

		/** The username associated with the Github account. */
		@JsonProperty("username")
		private String username;

		/** The email associated with the Github account. */
		@JsonProperty("email")
		private String email;

		/** The name associated with the Github account. */
		@JsonProperty("name")
		private String name;

	}

	@Data
	@ToString(callSuper = false)
	@EqualsAndHashCode(callSuper = true)
	public static final class Other extends LinkedAccount {

		@JsonProperty("type")
		private String type;

		@JsonAnySetter
		@JsonAnyGetter
		@JsonIgnore
		private Map<String, Object> properties;

	}

}