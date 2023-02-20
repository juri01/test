package com.example.webfluxelastic.dto.elasticSearch;

import lombok.Data;

@Data
public class Caller {
    private String lineNumber;
    private String className;
    private String methodName;
    private String fileName;
}
