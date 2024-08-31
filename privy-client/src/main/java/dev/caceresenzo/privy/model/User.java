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

}