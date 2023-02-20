package com.example.webfluxelastic.dto.elasticSearch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Elastic {
	private int took;
	@JsonProperty("timed_out")
	private boolean timedOut;
	@JsonProperty("_shards")
	private Shards shards;
	private MainHits hits;
}