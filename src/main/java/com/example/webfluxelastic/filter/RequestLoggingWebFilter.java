package com.example.webfluxelastic.filter;

import com.example.webfluxelastic.common.Constant;
import com.example.webfluxelastic.dto.RequestLogging;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class RequestLoggingWebFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange)
                .contextWrite(ctx -> ctx.put(Constant.REQUEST_INFO, RequestLogging.builder()
                        .requestAt(LocalDateTime.now()).uuid(UUID.randomUUID().toString()).build()));
    }
}
