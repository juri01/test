package com.example.mvc.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.SpringDocConfiguration;
import org.springdoc.webmvc.core.SpringDocWebMvcConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SpringDocConfiguration.class, SpringDocWebMvcConfiguration.class})
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("Example API").version("1.0.0"));
    }
}

