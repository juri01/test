package com.example.webfluxelastic.filter;


import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class ReactiveRequestContextHolder {
    public static final Class<ServerWebExchange> CONTEXT_KEY = ServerWebExchange.class;

    public static Mono<Object> getAttribute(String str) {
        return Mono.subscriberContext()
                .map(ctx -> ctx.get(CONTEXT_KEY).getAttribute(str));
    }
}