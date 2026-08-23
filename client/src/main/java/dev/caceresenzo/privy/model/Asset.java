package dev.caceresenzo.privy.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;

/** An asset involved in a wallet transfer. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true, defaultImpl = Asset.Other.class)
@JsonSubTypes({
	@JsonSubTypes.Type(value = Asset.NativeToken.class, name = "native-token"),
	@JsonSubTypes.Type(value = Asset.ERC20.class, name = "erc20"),
	@JsonSubTypes.Type(value = Asset.SAC.class, name = "sac"),
	@JsonSubTypes.Type(value = Asset.SPL.class, name = "spl"),
	@JsonSubTypes.Type(value = Asset.TRC20.class, name = "trc20"),
})
public sealed interface Asset {

	/** A native token asset (e.g. ETH, SOL). */
	@Data
	public static final class NativeToken implements Asset {}

	/** An ERC-20 token asset. */
	@Data
	public static final class ERC20 implements Asset {

		@JsonProperty("address")
		private String address;

	}

	/** A Stellar Asset Contract (SAC) asset. */
	@Data
	public static final class SAC implements Asset {

		@JsonProperty("address")
		private String address;

	}

	/** A Solana SPL token asset. */
	@Data
	public static final class SPL implements Asset {

		@JsonProperty("mint")
		private String mint;

	}

	/** A Tron TRC-20 token asset. */
	@Data
	public static final class TRC20 implements Asset {

		@JsonProperty("address")
		private String address;

	}

	@Data
	public static final class Other implements Asset {

		@JsonProperty("type")
		private String type;

		@JsonAnySetter
		@JsonAnyGetter
		private Map<String, Object> properties;

	}

}