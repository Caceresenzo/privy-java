package dev.caceresenzo.privy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.Map;

import org.junit.jupiter.api.Test;

import dev.caceresenzo.privy.util.PrivyMapper;
import lombok.SneakyThrows;

class AssetTest {

	@Test
	void nativeToken() {
		final var receivedAsset = read("""
			{
				"type": "native-token",
				"address": null
			}
			""");

		assertInstanceOf(Asset.NativeToken.class, receivedAsset);
	}

	@Test
	void erc20() {
		final var receivedAsset = read("""
			{
				"type": "erc20",
				"address": "0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48"
			}
			""");

		final var event = assertInstanceOf(Asset.ERC20.class, receivedAsset);

		assertEquals("0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48", event.getAddress());
	}

	@Test
	void sac() {
		final var receivedAsset = read("""
			{
				"type": "sac",
				"address": "USDC-GA5ZSEJYB37JRC5AVCIA5MOP4RHTM335X2KGX3IHOJAPP5RE34K4KZVN"
			}
			""");

		final var event = assertInstanceOf(Asset.SAC.class, receivedAsset);

		assertEquals("USDC-GA5ZSEJYB37JRC5AVCIA5MOP4RHTM335X2KGX3IHOJAPP5RE34K4KZVN", event.getAddress());
	}

	@Test
	void spl() {
		final var receivedAsset = read("""
			{
				"type": "spl",
				"mint": "EPjFWdd5AufqSSqeM2qN1xzybapC8G4wEGGkZwyTDt1v"
			}
			""");

		final var event = assertInstanceOf(Asset.SPL.class, receivedAsset);

		assertEquals("EPjFWdd5AufqSSqeM2qN1xzybapC8G4wEGGkZwyTDt1v", event.getMint());
	}

	@Test
	void trc20() {
		final var receivedAsset = read("""
			{
				"type": "trc20",
				"address": "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t"
			}
			""");

		final var event = assertInstanceOf(Asset.TRC20.class, receivedAsset);

		assertEquals("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t", event.getAddress());
	}

	@Test
	void other() {
		final var receivedAsset = read("""
			{
				"type": "something-else",
				"hello": "world"
			}
			""");

		final var event = assertInstanceOf(Asset.Other.class, receivedAsset);

		assertEquals("something-else", event.getType());
		assertEquals(Map.of("hello", "world"), event.getProperties());
	}

	@SneakyThrows
	private Asset read(String json) {
		return PrivyMapper.INSTANCE.readValue(json, Asset.class);
	}

}