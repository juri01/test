package com.example.webfluxelastic.dto.elasticSearch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Source {
	private String name;
	private String price;
	private String stream;
	private String time;
	@JsonProperty("service_name")
	private String serviceName;
	private String message;
	private String thread;
	@JsonProperty("message_exception")
	private String messageException;
	private String logTag;
	private String logger;
	private String marker;
	private Caller caller;
	@JsonProperty("k8s_cluster_name")
	private String k8sClusterName;
	private String traceId;
	@JsonProperty("tailed_path_hostname")
	private String tailedPathHostname;
	private String timestamp;
	private String version;
	private String level;
	private String hostname;
	@JsonProperty("index_name")
	private String indexName;
	private String spanId;
	private String index;
	@JsonProperty("custom_log")
	private String customLog;
}