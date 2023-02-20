package com.example.webfluxelastic.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RequestLogging {
    private LocalDateTime requestAt;
    private String uuid;
}
