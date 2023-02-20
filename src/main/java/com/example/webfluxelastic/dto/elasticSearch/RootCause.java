package com.example.webfluxelastic.dto.elasticSearch;

import lombok.Data;

@Data
public class RootCause {
	private String type;
	private String reason;
	private String resourceType;
	private String resourceId;
	private String indexUuid;
	private String index;
}