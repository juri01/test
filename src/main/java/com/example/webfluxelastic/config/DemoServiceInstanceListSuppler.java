package com.example.webfluxelastic.config;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Component
public class DemoServiceInstanceListSuppler implements ServiceInstanceListSupplier {

    private final String serviceId="say-hello";

    @Value("${cpay.elasticsearch.firstHost}")
    private String firstHost;
    @Override
    public String getServiceId() {
        return serviceId;
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return Flux.just(Arrays
                .asList(new DefaultServiceInstance(serviceId + "1", serviceId, firstHost, 9200, false),
                        new DefaultServiceInstance(serviceId + "2", serviceId, firstHost, 9200, false),
                        new DefaultServiceInstance(serviceId + "3", serviceId, firstHost, 9200, false)));
    }
}
