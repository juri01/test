package com.example.webfluxelastic.dto.elasticSearch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MainHits {
	private Total total;
	@JsonProperty("max_score")
	private int maxScore;
	private List<Hits> hits;
}