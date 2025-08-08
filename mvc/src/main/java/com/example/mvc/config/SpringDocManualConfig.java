package com.example.mvc.config;

import org.springdoc.core.SpringDocConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocManualConfig {

    @Bean
    public SpringDocConfigProperties springDocConfigProperties() {
        SpringDocConfigProperties props = new SpringDocConfigProperties();
        // 버전에 따라 제공되는 옵션명(예: setCacheDisabled)이 다를 수 있습니다.
        props.isCacheDisabled();
        return props;

    }
}
