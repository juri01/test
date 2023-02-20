package com.example.webfluxelastic.config;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Operators;

//@Configuration
public class MdcContextLifterConfiguration {

    public static final String MDC_CONTEXT_REACTOR_KEY =
            MdcContextLifterConfiguration.class.getName();

    @PostConstruct
    @SuppressWarnings("unchecked")
    public void contextOperatorHook() {
        Hooks.onEachOperator(
                MDC_CONTEXT_REACTOR_KEY,
                Operators.lift((scannable, subscriber) -> new MdcContextLifter(subscriber)));
    }

    @PreDestroy
    public void cleanupHook() {
        Hooks.resetOnEachOperator(MDC_CONTEXT_REACTOR_KEY);
    }
}
