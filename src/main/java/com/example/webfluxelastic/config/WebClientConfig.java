package com.example.webfluxelastic.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.resources.LoopResources;

import java.util.concurrent.TimeUnit;


@Configuration
@LoadBalancerClient(name = "say-hello", configuration = SayHelloConfiguration.class)
public class WebClientConfig {

    @LoadBalanced
    @Bean
    WebClient.Builder webClientBuilder() {
        ConnectionProvider connectionProvider = ConnectionProvider
                .builder("webclient-conn-pool")
                .build();

        HttpClient httpClient = HttpClient.create(connectionProvider)
                .runOn(LoopResources.create("webclient-event-"))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,1000)
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(1, TimeUnit.MICROSECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(1,TimeUnit.MICROSECONDS)));

        return WebClient.builder()
                .baseUrl("http://say-hello/")
                .clientConnector(new ReactorClientHttpConnector(httpClient));

    }

}
