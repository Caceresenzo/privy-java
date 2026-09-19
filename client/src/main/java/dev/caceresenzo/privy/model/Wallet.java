package dev.caceresenzo.privy.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import dev.caceresenzo.privy.util.serial.UnixDateDeserializer;
import lombok.Data;

@Data
public class Wallet {

	@JsonProperty("id")
	private String id;

	@JsonProperty("address")
	private String address;

	@JsonProperty("display_name")
	private String displayName;

	@JsonProperty("external_id")
	private String externalId;

	@JsonProperty("chain_type")
	private String chainType;

	@JsonProperty("created_at")
	@JsonDeserialize(using = UnixDateDeserializer.AsMilliseconds.class)
	private Date createdAt;

	@JsonProperty("exported_at")
	@JsonDeserialize(using = UnixDateDeserializer.AsMilliseconds.class)
	private Date exportedAt;

	@JsonProperty("imported_at")
	@JsonDeserialize(using = UnixDateDeserializer.AsMilliseconds.class)
	private Date importedAt;

	@JsonProperty("archived_at")
	@JsonDeserialize(using = UnixDateDeserializer.AsMilliseconds.class)
	private Date archivedAt;

}