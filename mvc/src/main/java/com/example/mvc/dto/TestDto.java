package com.example.mvc.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Schema(description = "코드 관리 DTO")
public class TestDto {
    @Schema(description = "코드 타입")
    private String name;
}
