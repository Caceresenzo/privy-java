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
		- [Find a User by a Github Username](#find-a-user-by-a-github-username)
		- [Find a User by a Custom Auth Id](#find-a-user-by-a-custom-auth-id)
		- [Set Custom Metadata for a User](#set-custom-metadata-for-a-user)
		- [Delete a User by an ID](#delete-a-user-by-an-id)
		- [Linked Accounts](#linked-accounts)
		- [Get the Verification Key](#get-the-verification-key)
		- [Verity an Auth Token](#verity-an-auth-token)
		- [Get a User from an ID Token](#get-a-user-from-an-id-token)
	- [Advanced Configuration](#advanced-configuration)
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
Optional<User> user = client.findUserByPhone("+1 (234) 567-8912");
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

### Find a User by a Github Username

```java
Optional<User> user = client.findUserByGithubUsername("johndoe");
```

### Find a User by a Custom Auth Id

```java
Optional<User> user = client.findUserByCustomAuthId("123456");
```

### Set Custom Metadata for a User

```java
CustomMetadata metadata = new CustomMetadata();
metadata.putString("planet", "Earth");
metadata.putNumber("age", 42);
metadata.putBoolean("powerful", true);

String userId = "a0b1c2d3e4f5g6h7i8j9k0l1m";

User updatedUser = client.setCustomMetadata(userId, metadata);
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

### Get the Verification Key

```java
import java.security.PublicKey;

PublicKey verificationKey = client.getVerificationKey();
```

> [!NOTE]
> The verification key is cached by default. <br />
> This behaviour can be disabled via the `.cacheVerificationKey(false)` method when building the client.

### Verity an Auth Token

```java
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

String token = request.getCookie("privy-token");
Jws<Claims> jwt = client.verifyAuthToken(token);

Claims payload = jwt.getPayload();
System.out.println("User ID: %s".formatted(payload.getSubject()));
```

> [!TIP]
> We recommend keeping the verification key caching enabled (default behavior) if it is being used for authenticating requests.

### Get a User from an ID Token

```java
String idToken = request.getCookie("privy-id-token");
User user = client.getUserFromIdToken(idToken);
```

> [!TIP]
> We recommend keeping the verification key caching enabled (default behavior) if it is being used for authenticating requests.

## Advanced Configuration

The client can be configured further to meet the demands of the application:

```java
PrivyClient client = PrivyClient.builder()

	/* change the api url */
	.apiUrl("https://auth.privy.io")

	/* mandatory credentials */
	.applicationId("a0b1c2d3e4f5g6h7i8j9k0l1m")
	.applicationSecret("a0b1c2d3e4f5g6h7i8j9k0l1m2n3o4p5q6r7s8t9u0v1w2x3y4z5a6b7c8d9e0f1g2h3i4j5k6l7m8n9o0p1q2r3")

	/* change the iterator page size */
	.maxPageSize(100)

	/* should the key obtained via `client.getVerificationKey()` be cached? */
	.cacheVerificationKey(true)

	/* configure the JWT parser, usually not recommended, but can be useful for testing purposes. */
	.jwtParserCustomizer((builder) -> builder
		.clockSkewSeconds(60)
		.clock(new FixedClock(System.currentTimeMillis())) /* stop the world */
		.unsecured() /* enable `alg: none` */
	)

	.build();
```

> [!NOTE]
> All values except those for `applicationId`, `applicationSecret` and `jwtParserCustomizer` are the default values.

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
switch (receivedEvent) {
	case Event.Test event -> {
		System.out.println("Testing: %s".formatted(event.getMessage()));
	}

	case Event.UserCreated event -> {
		System.out.println("User Created: %s".formatted(event.getUser().getId()));
	}

	case Event.UserAuthenticated event -> {
		System.out.println("User Authenticated: %s".formatted(event.getUser().getId()));
		System.out.println(" with account: %s".formatted(event.getAccount()));
	}

	case Event.UserLinkedAccount event -> {
		System.out.println("User Linked Account: %s".formatted(event.getUser().getId()));
		System.out.println(" with account: %s".formatted(event.getAccount()));
	}

	case Event.UserUnlinkedAccount event -> {
		System.out.println("User Unlinked Account: %s".formatted(event.getUser().getId()));
		System.out.println(" with account: %s".formatted(event.getAccount()));
	}

	case Event.UserUpdatedAccount event -> {
		System.out.println("User Updated Account: %s".formatted(event.getUser().getId()));
		System.out.println(" with account: %s".formatted(event.getAccount()));
	}

	case Event.UserTransferredAccount event -> {
		System.out.println("User Transferred Account: %s -> %s".formatted(event.getFromUser().getId(), event.getToUser().getId()));
		System.out.println(" with account: %s".formatted(event.getAccount()));
		System.out.println(" and the old user was deleted? %s".formatted(event.isDeleted()));
	}

	case Event.UserWalletCreated event -> {
		System.out.println("User Wallet Created: %s".formatted(event.getUserId()));
		System.out.println(" with wallet address: %s".formatted(event.getWallet().getAddress()));
	}

	case Event.MultiFactorAuthenticationEnabled event -> {
		System.out.println("Multi Factor Authentication Enabled: %s".formatted(event.getUserId()));
		System.out.println(" with method: %s".formatted(event.getMethod()));
	}

	case Event.MultiFactorAuthenticationDisabled event -> {
		System.out.println("Multi Factor Authentication Disabled: %s".formatted(event.getUserId()));
		System.out.println(" with method: %s".formatted(event.getMethod()));
	}

	case Event.PrivateKeyExported event -> {
		System.out.println("Private Key Exported: %s".formatted(event.getUserId()));
		System.out.println(" with wallet address: %s".formatted(event.getWalletAddress()));
	}

	case Event.WalletRecoverySetup event -> {
		System.out.println("Wallet Recovery Setup: %s".formatted(event.getUserId()));
		System.out.println(" with wallet address: %s".formatted(event.getWalletAddress()));
		System.out.println(" with method: %s".formatted(event.getMethod()));
	}

	case Event.WalletRecovered event -> {
		System.out.println("Wallet Recovered: %s".formatted(event.getUserId()));
		System.out.println(" with wallet address: %s".formatted(event.getWalletAddress()));
	}

	case Event.Other event -> {
		System.out.println("Unknown event: %s".formatted(event.getType()));
		System.out.println(" with properties: %s".formatted(event.getProperties()));
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
