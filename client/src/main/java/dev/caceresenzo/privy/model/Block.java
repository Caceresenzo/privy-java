package dev.caceresenzo.privy.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Block {

	@JsonProperty("number")
	private long number;

	@JsonProperty("timestamp")
	private Date timestamp;

}