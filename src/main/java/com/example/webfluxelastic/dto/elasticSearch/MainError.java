package com.example.webfluxelastic.dto.elasticSearch;

import lombok.Data;

@Data
public class MainError {
	private Error error;
	private int status;
}