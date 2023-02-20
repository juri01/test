package com.example.webfluxelastic.service;

import com.example.webfluxelastic.dto.elasticSearch.Elastic;
import com.example.webfluxelastic.dto.elasticSearch.query.QueryMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElasticSearchService {
    private final ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory;
   private final WebClient.Builder loadBalancedWebClientBuilder;
   // private final ReactorLoadBalancerExchangeFilterFunction lbFunction;
   //private final WebClient webClient;
   //private final ReactiveCircuitBreaker reactiveCircuitBreaker;
/*
    public ElasticSearchService(WebClient.Builder webclient, ReactiveCircuitBreakerFactory circuitBreakerFactory) {
        this.webClient = webclient.build();
        this.readingListCircuitBreaker = circuitBreakerFactory.create("recommended");
    }*/
/*

    public Mono<String> client() {
        WebClient webClient = WebClient.create("http://localhost:9200");
log.info("clinet");

        return webClient.get().uri("/product/_search")
                .retrieve()
                .bodyToMono(String.class)
                .transform(it -> reactiveCircuitBreakerFactory.create("slow")
                        .run(it, throwable ->  Mono.just("fallback")));

        return webClient.get().uri("/slow").retrieve().bodyToMono(String.class).transform(
                it -> cbFactory.create("slow").run(it, throwable -> return Mono.just("fallback")));
    }


        public Mono<String> slow() {
            log.info("clinet");
            return loadBalancedWebClientBuilder.build().get().uri("http://say-hello/product/_search").retrieve().bodyToMono(String.class).transform(
                    it -> reactiveCircuitBreakerFactory.create("slow").run(it,throwable -> Mono.just("ss") ));
        }
*/
    public Mono<Elastic> fieldQuerySearch(String elasticSearchIndex, String field, String query) {
        //WebClient webClient = WebClient.create("http://localhost:9200");
        log.info("fieldQuerySearch index {}, field {}, query {}", elasticSearchIndex, field, query);
        return loadBalancedWebClientBuilder.build()
                .post()
                .uri(elasticSearchIndex.concat("/_search"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(QueryMessage.Querys.builder()
                        .query(QueryMessage.Query.builder()
                                .queryString(QueryMessage.QueryString.builder()
                                        .defaultField(field)
                                        .query(query)
                                        .build())
                                .build())
                        .build())
                .retrieve()
                .bodyToMono(Elastic.class);
               // .transform(
                //        it -> reactiveCircuitBreakerFactory.create("slow").run(it,throwable -> { throw throwable;}));
/*
        return Mono.just(new Elastic());});*/
                //.transform(it -> reactiveCircuitBreakerFactory.create("webclientBreaker").run(it, throwable -> {
                //    log.info("reactiveCircuitBreakerFactory");
                //    return Mono.just(new Elastic());}));
                /*.onErrorResume(throwable -> {
                    log.info("elasticSearch {}", throwable.getMessage());
                    return Mono.just(new Elastic());
                });

                 */
        /*
        private String getRetryFallback(Throwable t) {
            return "getRetryFallback! exception type: " + t.getClass() + "exception, message: " + t.getMessage();
        }

         */
    }
}
