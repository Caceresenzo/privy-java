package dev.caceresenzo.privy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ApplicationSettings {

	@JsonProperty("id")
	private String id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("verification_key")
	private String verificationKey;

	@JsonProperty("logo_url")
	private String logoUrl;

	@JsonProperty("theme")
	private String theme;

	@JsonProperty("accent_color")
	private String accentColor;

	@JsonProperty("wallet_auth")
	private boolean walletAuth;

	@JsonProperty("email_auth")
	private boolean emailAuth;

	@JsonProperty("sms_auth")
	private boolean smsAuth;

	@JsonProperty("google_oauth")
	private boolean googleOAuth;

	@JsonProperty("twitter_oauth")
	private boolean twitterOAuth;

	@JsonProperty("discord_oauth")
	private boolean discordOAuth;

	@JsonProperty("github_oauth")
	private boolean githubOAuth;

	@JsonProperty("apple_oauth")
	private boolean appleOAuth;

	@JsonProperty("linkedin_oauth")
	private boolean linkedInOAuth;

	@JsonProperty("tiktok_oauth")
	private boolean tiktokOAuth;

	@JsonProperty("terms_and_conditions_url")
	private String termsAndConditionsUrl;

	@JsonProperty("privacy_policy_url")
	private String privacyPolicyUrl;

}