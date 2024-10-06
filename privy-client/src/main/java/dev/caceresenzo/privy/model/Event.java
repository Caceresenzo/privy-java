package dev.caceresenzo.privy.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true, defaultImpl = Event.Other.class)
@JsonSubTypes({
	@JsonSubTypes.Type(value = Event.Test.class, name = "privy.test"),
	@JsonSubTypes.Type(value = Event.UserCreated.class, name = "user.created"),
	@JsonSubTypes.Type(value = Event.UserAuthenticated.class, name = "user.authenticated"),
	@JsonSubTypes.Type(value = Event.UserLinkedAccount.class, name = "user.linked_account"),
	@JsonSubTypes.Type(value = Event.UserUnlinkedAccount.class, name = "user.unlinked_account"),
	@JsonSubTypes.Type(value = Event.UserUpdatedAccount.class, name = "user.updated_account"),
})
public interface Event {

	/** A user was created in the application. */
	@Data
	public static final class Test implements Event {

		@JsonProperty("message")
		private String message;

	}

	/** A user was created in the application. */
	@Data
	public static final class UserCreated implements Event {

		@JsonProperty("user")
		private User user;

	}

	/** A user successfully logged into the application. */
	@Data
	public static final class UserAuthenticated implements Event {

		@JsonProperty("account")
		private LinkedAccount account;

		@JsonProperty("user")
		private User user;

	}

	/** A user successfully linked a new login method. */
	@Data
	public static final class UserLinkedAccount implements Event {

		@JsonProperty("account")
		private LinkedAccount account;

		@JsonProperty("user")
		private User user;

	}

	/** A user successfully unlinked an existing login method. */
	@Data
	public static final class UserUnlinkedAccount implements Event {

		@JsonProperty("account")
		private LinkedAccount account;

		@JsonProperty("user")
		private User user;

	}

	/** A user successfully updates the email or phone number linked to their account. */
	@Data
	public static final class UserUpdatedAccount implements Event {

		@JsonProperty("account")
		private LinkedAccount account;

		@JsonProperty("user")
		private User user;

	}

	@Data
	public static final class Other implements Event {

		@JsonProperty("type")
		private String type;

		@JsonAnySetter
		@JsonAnyGetter
		private Map<String, Object> properties;

	}

}