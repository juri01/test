package com.example.webfluxelastic.controller;

import com.example.webfluxelastic.common.Constant;
import com.example.webfluxelastic.dto.ApiResponse;
import com.example.webfluxelastic.facade.DataEyeSearchFacade;
import com.example.webfluxelastic.filter.ReactiveRequestContextHolder;
import com.example.webfluxelastic.property.ElasticSearchHostProperties;
import com.example.webfluxelastic.type.ResponseType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DataEyeSearchController {

    private final DataEyeSearchFacade dataEyeSearchFacade;

    @GetMapping("/")
    public void test() {
        log.info("test");
    }

    @GetMapping("/compoundTaxation/{tid}")
    public Mono<Object> compoundTaxation(@PathVariable("tid") String tid, ServerWebExchange exchange) {
        log.info("compoundTaxation 서비스 요청 {}", tid);

/*
        Flux<Integer> integerFlux =
                Flux.range(2017, 2) // 2017, 2018
                        .publishOn(Schedulers.boundedElastic()) // 다른 워커에서 작업 처리
                        .log();  // 로깅 onNext, onComplete, onError
                        //.subscriberContext(ctx -> ctx.put(LogConstant.TRACING_ID, LoggingUtil.getTraceId())); // Context 전달

        integerFlux.subscribe(year -> log.info("year ::: {}", year));

 */

        return dataEyeSearchFacade.searchCompoundTaxation(tid)
                .log()
                .map(result -> {
                    log.info("compoundTaxation 조회 결과 = tid : {}, taxinfo : {}", tid, result);
                    return ApiResponse.ok(result);
                });

         /*
        return dataEyeSearchFacade.searchCompoundTaxation(tid)
                .log()
                .flatMap(s-> Mono.deferContextual(ctx -> {
                    log.info("compoundTaxation 조회 결과 = tid : {}, taxinfo : {}", tid, "d");
                    return Mono.just(ApiResponse.ok(ctx.get(Constant.REQUEST_INFO), s));
                }));*/
    }
}
