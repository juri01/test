package com.example.webfluxelastic.dto.elasticSearch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Hits {
	@JsonProperty("_index")
	private String index;
	@JsonProperty("_id")
	private String id;
	@JsonProperty("_score")
	private int score;
	@JsonProperty("_source")
	private Source source;
}