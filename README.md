# Privy Client for Java

This Java client connects with Privy.io, enabling simple user management and secure event handling. It also includes a Spring Boot starter for quick integration.

- [Privy Client for Java](#privy-client-for-java)
- [Installation](#installation)
- [Client](#client)
	- [Configuration](#configuration)
	- [Usage](#usage)
		- [Stream Users](#stream-users)
		- [Stream Users by a Search Term](#stream-users-by-a-search-term)
		- [Find a User by an ID](#find-a-user-by-an-id)
		- [Find a User by an Email Address](#find-a-user-by-an-email-address)
		- [Find a User by a Wallet Address](#find-a-user-by-a-wallet-address)
		- [Find a User by a Phone Number](#find-a-user-by-a-phone-number)
		- [Find a User by a Twitter Username](#find-a-user-by-a-twitter-username)
		- [Find a User by a Twitter Subject](#find-a-user-by-a-twitter-subject)
		- [Find a User by a Discord Username](#find-a-user-by-a-discord-username)
		- [Delete a User by an ID](#delete-a-user-by-an-id)
		- [Linked Accounts](#linked-accounts)
- [Webhook](#webhook)
	- [Configuration](#configuration-1)
	- [Usage](#usage-1)
		- [Verify an Event](#verify-an-event)
- [Spring Boot Starter](#spring-boot-starter)
	- [Client](#client-1)
	- [Webhook](#webhook-1)
		- [Controller Example](#controller-example)
	- [Spring OAuth 2.0 Resource Server](#spring-oauth-20-resource-server)
		- [Controller Example](#controller-example-1)

# Installation

```xml
<properties>
    <privy.version>0.7.0</privy.version>
</properties>

<dependencies>
    <dependency>
        <groupId>dev.caceresenzo.privy</groupId>
        <artifactId>client</artifactId>
        <version>${privy.version}</version>
    </dependency>
</dependencies>
```

# Client

## Configuration

```java
PrivyClient client = PrivyClient.builder()
	.applicationId("a0b1c2d3e4f5g6h7i8j9k0l1m")
	.applicationSecret("a0b1c2d3e4f5g6h7i8j9k0l1m2n3o4p5q6r7s8t9u0v1w2x3y4z5a6b7c8d9e0f1g2h3i4j5k6l7m8n9o0p1q2r3")
	.build();
```

## Usage

### Stream Users

```java
Stream<User> users = client.findAllUsers();

/* or get a list via */
List<User> users = client.findAllUsers().toList();
```

### Stream Users by a Search Term

```java
Stream<User> users = client.findAllUsers("john");

/* or get a list via */
List<User> users = client.findAllUsers("john").toList();
```

### Find a User by an ID

```java
Optional<User> user = client.findUserById("did:privy:a0b1c2d3e4f5g6h7i8j9k0l1m");

/* the "did:privy:" prefix is optional */
Optional<User> user = client.findUserById("a0b1c2d3e4f5g6h7i8j9k0l1m");
```

### Find a User by an Email Address

```java
Optional<User> user = client.findUserByEmail("john.doe@gmail.com");
```

### Find a User by a Wallet Address

```java
Optional<User> user = client.findUserByWallet("0xa0b1c2d3e4f5g6h7i8j9k0l1m2n3o4p5q6r7s8t9");
```

### Find a User by a Phone Number

```java
Optional<User> user = client.findUserByWallet("+1 (234) 567-8912");
```

### Find a User by a Twitter Username

```java
Optional<User> user = client.findUserByTwitterUsername("johndoe");
```

### Find a User by a Twitter Subject

```java
Optional<User> user = client.findUserByTwitterSubject("1234567890");
```

### Find a User by a Discord Username

```java
Optional<User> user = client.findUserByDiscordUsername("johndoe#0");
```

### Delete a User by an ID

```java
boolean deleted = client.deleteUserById("0xa0b1c2d3e4f5g6h7i8j9k0l1m2n3o4p5q6r7s8t9");
```

### Linked Accounts

```java
User user = client.findUserById("a0b1c2d3e4f5g6h7i8j9k0l1m").orElseThrow();

/* access it via known type */
Optional<LinkedAccount.Email> email = user.getEmail();
Optional<LinkedAccount.Google> google = user.getGoogle();
Optional<LinkedAccount.Github> github = user.getGithub();

/* access it via class */
Optional<LinkedAccount.Email> email = user.getAccount(LinkedAccount.Email.class);
Optional<LinkedAccount.Google> google = user.getAccount(LinkedAccount.Google.class);
Optional<LinkedAccount.Github> github = user.getAccount(LinkedAccount.Github.class);

/* iterate over accounts */
for (LinkedAccount account : user.getLinkedAccounts()) {
	System.out.println(account);

	// switch (account)
	// see "Testing the linked account type"
}
```

<details>
<summary>Testing the linked account type</summary>

```java
switch (account) {
	case LinkedAccount.Wallet wallet -> {
		System.out.println("Wallet");
		System.out.println(" with address: %s".formatted(wallet.getAddress()));
		System.out.println(" with chain id: %s".formatted(wallet.getChainId()));
	}

	case LinkedAccount.Email email -> {
		System.out.println("Email");
		System.out.println(" with address: %s".formatted(email.getAddress()));
	}

	case LinkedAccount.Phone phone -> {
		System.out.println("Phone");
		System.out.println(" with number: %s".formatted(phone.getNumber()));
	}

	case LinkedAccount.Google google -> {
		System.out.println("Google %s".formatted(google.getSubject()));
		System.out.println(" with email: %s".formatted(google.getEmail()));
		System.out.println(" with name: %s".formatted(google.getName()));
	}

	case LinkedAccount.Twitter twitter -> {
		System.out.println("Twitter %s".formatted(twitter.getSubject()));
		System.out.println(" with username: %s".formatted(twitter.getUsername()));
		System.out.println(" with name: %s".formatted(twitter.getName()));
	}

	case LinkedAccount.Discord discord -> {
		System.out.println("Discord %s".formatted(discord.getSubject()));
		System.out.println(" with username: %s".formatted(discord.getUsername()));
		System.out.println(" with email: %s".formatted(discord.getEmail()));
	}

	case LinkedAccount.Github github -> {
		System.out.println("Github %s".formatted(github.getSubject()));
		System.out.println(" with username: %s".formatted(github.getUsername()));
		System.out.println(" with name: %s".formatted(github.getName()));
	}

	case LinkedAccount.Other other -> {
		System.out.println("Unknown %s".formatted(other.getType()));
		System.out.println(" with properties: %s".formatted(other.getProperties()));
	}
}
```
</details>

# Webhook

## Configuration

```java
PrivyWebhook webhook = PrivyWebhook.builder()
	.signingKey("whsec_a0b1c2d3e4f5g6h7i8j9k0l1m2n3o4p5")
	.build();
```

## Usage

### Verify an Event

```java
PrivyWebhook.Headers headers = new PrivyWebhook.Headers(
	"msg_a0b1c2d3e4f5g6h7i8j9k0l1m2n",
	"1234567890",
	"v1,a0b1c2d3e4f5g6h7i8j9k0l1m2n3o4p5q6r7s8t9u0v1"
);

String body = """
	{
	  "message": "Hello, World!",
	  "type": "privy.test"
	}
	""";

Event event = webhook.verify(
	headers,
	body
);
```

<details>
<summary>Testing the event type</summary>

```java
switch (event) {
	case Event.Test test -> {
		System.out.println("Testing: %s".formatted(test.getMessage()));
	}

	case Event.UserCreated userCreated -> {
		System.out.println("User Created: %s".formatted(userCreated.getUser().getId()));
	}

	case Event.UserAuthenticated userAuthenticated -> {
		System.out.println("User Authenticated: %s".formatted(userAuthenticated.getUser().getId()));
		System.out.println(" with account: %s".formatted(userAuthenticated.getAccount()));
	}

	case Event.UserLinkedAccount userLinkedAccount -> {
		System.out.println("User Linked Account: %s".formatted(userLinkedAccount.getUser().getId()));
		System.out.println(" with account: %s".formatted(userLinkedAccount.getAccount()));
	}

	case Event.UserUnlinkedAccount userUnlinkedAccount -> {
		System.out.println("User Unlinked Account: %s".formatted(userUnlinkedAccount.getUser().getId()));
		System.out.println(" with account: %s".formatted(userUnlinkedAccount.getAccount()));
	}

	case Event.UserUpdatedAccount userUpdatedAccount -> {
		System.out.println("User Updated Account: %s".formatted(userUpdatedAccount.getUser().getId()));
		System.out.println(" with account: %s".formatted(userUpdatedAccount.getAccount()));
	}

	case Event.UserTransferredAccount userTransferredAccount -> {
		System.out.println("User Transferred Account: %s -> %s".formatted(userTransferredAccount.getFromUser().getId(), userTransferredAccount.getToUser().getId()));
		System.out.println(" with account: %s".formatted(userTransferredAccount.getAccount()));
		System.out.println(" and the old user was deleted? %s".formatted(userTransferredAccount.isDeleted()));
	}

	case Event.Other other -> {
		System.out.println("Unknown event: %s".formatted(other.getType()));
		System.out.println(" with properties: %s".formatted(other.getProperties()));
	}
}
```
</details>

# Spring Boot Starter

There is a Spring Boot auto-configuration available.

```xml
<dependencies>
    <dependency>
        <groupId>dev.caceresenzo.privy</groupId>
        <artifactId>spring-boot-starter</artifactId>
        <version>${privy.version}</version>
    </dependency>
</dependencies>
```

## Client

Which is enabled when the Application ID is specified in the configuration:

```yml
privy:
  application-id: a0b1c2d3e4f5g6h7i8j9k0l1m
  application-secret: a0b1c2d3e4f5g6h7i8j9k0l1m2n3o4p5q6r7s8t9u0v1w2x3y4z5a6b7c8d9e0f1g2h3i4j5k6l7m8n9o0p1q2r3
```

## Webhook

Which is enabled when the Webhook Signing Key is specified in the configuration:

```yml
privy:
  webhook-signing-key: whsec_a0b1c2d3e4f5g6h7i8j9k0l1m2n3o4p5
```

### Controller Example

```java
@RestController
@RequestMapping(path = "/privy/webhook", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProvyRestControllerV1 {

	private final PrivyWebhook privyWebhook;

	@PostMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void webhook(
		@RequestHeader(PrivyWebhook.Headers.ID_NAME) String id,
		@RequestHeader(PrivyWebhook.Headers.TIMESTAMP_NAME) String timestamp,
		@RequestHeader(PrivyWebhook.Headers.SIGNATURE_NAME) String signature,
		@RequestBody String body
	) {
		PrivyWebhook.Headers headers = new PrivyWebhook.Headers(id, timestamp, signature);
		Event event = privyWebhook.verify(headers, body);

		System.out.println(event);

		// switch (event)
		// see "Testing the event type"
	}

}
```

## Spring OAuth 2.0 Resource Server

In order for Privy authentication to work in the [Spring OAuth 2.0 Resource Server](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/index.html), it must be configured as follows:

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          jwk-set-uri: ${PRIVY_JWKS_ENDPOINT}
          jws-algorithms:
            - ES256
          issuer-uri: privy.io

          # Optional, but increases security
          audiences:
            - ${PRIVY_APPLICATION_ID}
```

### Controller Example

```java
@RestController
@RequestMapping(path = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
public class HelloRestController {

	@GetMapping
	@PreAuthorize("authenticated")
	public String greet(
		@AuthenticationPrincipal Jwt jwt
	) {
		String subject = jwt.getSubject();

		return "Welcome %s!".formatted(subject);
	}

}
```
