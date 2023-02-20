package com.example.webfluxelastic.controller;

import com.example.webfluxelastic.dto.ApiResponse;
import com.example.webfluxelastic.dto.CompoundTaxation;
import com.example.webfluxelastic.dto.elasticSearch.Elastic;
import com.example.webfluxelastic.facade.DataEyeSearchFacade;
import com.example.webfluxelastic.service.ElasticSearchService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static reactor.core.publisher.Mono.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = DataEyeSearchController.class)
//@Import(DataEyeSearchFacade.class)
class DataEyeSearchControllerTest {

    @MockBean
    DataEyeSearchFacade dataEyeSearchFacade;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        TestPublisher<String> testPublisher = TestPublisher.createCold();
        testPublisher.emit("aa");
        testPublisher.complete();
        given(dataEyeSearchFacade.searchCompoundTaxation("product")).willReturn(Mono.just(CompoundTaxation.builder().build()));
       // when((Publisher<?>) dataEyeSearchFacade.convertCompoundTaxation(new Elastic())).thenReturn(Mono.just(new Elastic()));
    }
    @Test
    public void hello() {

        webTestClient.get()
                .uri("/compoundTaxation/product")
                .exchange()
                .expectStatus().isOk();
                //.expectBody(ApiResponse.class)
                //.isEqualTo(ApiResponse.ok(CompoundTaxation.builder().tid("test").build()));


    }

}