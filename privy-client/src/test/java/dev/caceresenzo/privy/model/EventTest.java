package dev.caceresenzo.privy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.caceresenzo.privy.util.PrivyUtils;
import lombok.SneakyThrows;

class EventTest {

	static ObjectMapper mapper;

	@BeforeAll
	static void setUp() {
		mapper = PrivyUtils.createMapper();
	}

	@Test
	void test() {
		final var event = read("""
			{
				"type": "privy.test",
				"message": "Hello, World!"
			}
			""");

		final var test = assertInstanceOf(Event.Test.class, event);

		assertEquals("Hello, World!", test.getMessage());
	}

	@Test
	void userCreated() {
		final var event = read("""
			{
				"type": "user.created",
				"user": {
					"id": 42
				}
			}
			""");

		final var userCreated = assertInstanceOf(Event.UserCreated.class, event);

		assertEquals("42", userCreated.getUser().getId());
	}

	@Test
	void userAuthenticated() {
		final var event = read("""
			{
				"type": "user.authenticated",
				"account": {
					"type": "email"
				},
				"user": {
					"id": 42
				}
			}
			""");

		final var userAuthenticated = assertInstanceOf(Event.UserAuthenticated.class, event);

		assertInstanceOf(LinkedAccount.Email.class, userAuthenticated.getAccount());
		assertEquals("42", userAuthenticated.getUser().getId());
	}

	@Test
	void userLinkedAccount() {
		final var event = read("""
			{
				"type": "user.linked_account",
				"account": {
					"type": "email"
				},
				"user": {
					"id": 42
				}
			}
			""");

		final var userLinkedAccount = assertInstanceOf(Event.UserLinkedAccount.class, event);

		assertInstanceOf(LinkedAccount.Email.class, userLinkedAccount.getAccount());
		assertEquals("42", userLinkedAccount.getUser().getId());
	}

	@Test
	void userUnlinkedAccount() {
		final var event = read("""
			{
				"type": "user.unlinked_account",
				"account": {
					"type": "email"
				},
				"user": {
					"id": 42
				}
			}
			""");

		final var userUnlinkedAccount = assertInstanceOf(Event.UserUnlinkedAccount.class, event);

		assertInstanceOf(LinkedAccount.Email.class, userUnlinkedAccount.getAccount());
		assertEquals("42", userUnlinkedAccount.getUser().getId());
	}

	@Test
	void userUpdatedAccount() {
		final var event = read("""
			{
				"type": "user.updated_account",
				"account": {
					"type": "email"
				},
				"user": {
					"id": 42
				}
			}
			""");

		final var userUpdatedAccount = assertInstanceOf(Event.UserUpdatedAccount.class, event);

		assertInstanceOf(LinkedAccount.Email.class, userUpdatedAccount.getAccount());
		assertEquals("42", userUpdatedAccount.getUser().getId());
	}

	@SneakyThrows
	private Event read(String json) {
		return mapper.readValue(json, Event.class);
	}

	@AfterAll
	static void tearDown() {
		mapper = null;
	}

}