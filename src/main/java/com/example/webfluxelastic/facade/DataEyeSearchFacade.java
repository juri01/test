package com.example.webfluxelastic.facade;

import com.example.webfluxelastic.dto.CompoundTaxation;
import com.example.webfluxelastic.dto.elasticSearch.Elastic;
import com.example.webfluxelastic.service.ElasticSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataEyeSearchFacade {

    private final ElasticSearchService elasticSearchService;

    public Mono<CompoundTaxation> searchCompoundTaxation(String tid) {
        String elasticSearchIndex="product";
        String field ="name";
        String query = tid;
        return elasticSearchService.fieldQuerySearch(elasticSearchIndex, field, query)
                .map(this::convertCompoundTaxation);
    }

    public CompoundTaxation convertCompoundTaxation(Elastic elastic) {
        return CompoundTaxation.builder().tid("test").build();
    }
}
