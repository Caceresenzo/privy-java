package dev.caceresenzo.privy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Asset {

	@JsonProperty("type")
	private String type;

	@JsonProperty("address")
	private String address;

}