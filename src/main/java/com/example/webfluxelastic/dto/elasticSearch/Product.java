package com.example.webfluxelastic.dto.elasticSearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Product {
    private String name;
    private String price;
}
