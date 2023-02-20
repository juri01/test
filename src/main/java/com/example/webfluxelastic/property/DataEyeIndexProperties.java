package com.example.webfluxelastic.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cpay.data-eye.index")
@Getter
@Setter
@ToString
public class DataEyeIndexProperties {
    private String defaultIndex;
}
