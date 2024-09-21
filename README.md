# Privy Client for Java

## Installation

```xml
<properties>
    <privy.version>0.2.0</privy.version>
</properties>

<dependencies>
    <dependency>
        <groupId>dev.caceresenzo.privy</groupId>
        <artifactId>client</artifactId>
        <version>${privy.version}</version>
    </dependency>
</dependencies>
```

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
Stream<User> users = findAllUsers();

/* or get a list via */
List<User> users = findAllUsers().toList();
```

### Stream Users by a Search Term

```java
Stream<User> users = findAllUsers("john");

/* or get a list via */
List<User> users = findAllUsers("john").toList();
```

### Find a User by an ID

```java
Optional<User> user = client.findUserById("did:privy:a0b1c2d3e4f5g6h7i8j9k0l1m");
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

### Delete a User by an ID

```java
boolean deleted = client.deleteUserById("did:privy:a0b1c2d3e4f5g6h7i8j9k0l1m");
```

## Spring Boot Starter

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

Which is enabled when the Application ID is specified in the configuration:

```yml
privy:
  application-id: a0b1c2d3e4f5g6h7i8j9k0l1m
  application-secret: a0b1c2d3e4f5g6h7i8j9k0l1m2n3o4p5q6r7s8t9u0v1w2x3y4z5a6b7c8d9e0f1g2h3i4j5k6l7m8n9o0p1q2r3
```
