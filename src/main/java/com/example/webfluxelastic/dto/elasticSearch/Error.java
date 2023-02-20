package com.example.webfluxelastic.dto.elasticSearch;

import lombok.Data;

import java.util.List;

@Data
public class Error {
	private List<RootCause> rootCause;
	private String type;
	private String reason;
	private String resourceType;
	private String resourceId;
	private String indexUuid;
	private String index;
}