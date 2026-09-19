package dev.caceresenzo.privy.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import dev.caceresenzo.privy.util.serial.UnixDateDeserializer;
import lombok.Data;

@Data
public class Block {

	@JsonProperty("number")
	private long number;

	@JsonProperty("timestamp")
	@JsonDeserialize(using = UnixDateDeserializer.AsMilliseconds.class)
	private Date timestamp;

}