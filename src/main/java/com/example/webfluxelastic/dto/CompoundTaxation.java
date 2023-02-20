package com.example.webfluxelastic.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@Builder
public class CompoundTaxation {
    private String partnerId;
    private String tid;
    private BigDecimal totalAmount;
    private BigDecimal taxFreeAmount;
}
