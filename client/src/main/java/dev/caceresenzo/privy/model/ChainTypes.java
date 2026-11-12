package dev.caceresenzo.privy.model;

import lombok.experimental.UtilityClass;

/**
 * Chain types supported by Privy wallets.
 *
 * @see <a href="https://docs.privy.io/api-reference/wallets/create">Privy API - Create wallet</a>
 */
@UtilityClass
public class ChainTypes {

	public static final String ETHEREUM = "ethereum";
	public static final String SOLANA = "solana";
	public static final String COSMOS = "cosmos";
	public static final String STELLAR = "stellar";
	public static final String SUI = "sui";
	public static final String APTOS = "aptos";
	public static final String MOVEMENT = "movement";
	public static final String TRON = "tron";
	public static final String BITCOIN_SEGWIT = "bitcoin-segwit";
	public static final String BITCOIN_TAPROOT = "bitcoin-taproot";
	public static final String PEARL = "pearl";
	public static final String NEAR = "near";
	public static final String TON = "ton";
	public static final String STARKNET = "starknet";
	public static final String XRPL = "xrpl";
	public static final String SPARK = "spark";

}