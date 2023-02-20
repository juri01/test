package com.example.webfluxelastic.property;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cpay.elasticsearch.host")
@Getter
@Setter
@ToString
public class ElasticSearchHostProperties {
    private String firstHost;
    private String secondHost;
    private String thirdHost;
}
