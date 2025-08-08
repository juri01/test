package com.example.mvc.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "TestController", description = "테스트 엔드포인트")
@RequestMapping("/test")
@RestController
public class TestController {

    @Operation(summary = "01. 테스트 조회", description = "테스트 조회합니다.")
    @GetMapping("/test")
    public String test() {
        return "tt";
    }

    @Operation(summary = "02 . 테스트 조회", description = "테스트 조회합니다.")
    @GetMapping("/failover")
    public String test2() {
        return "tt";
    }

}
