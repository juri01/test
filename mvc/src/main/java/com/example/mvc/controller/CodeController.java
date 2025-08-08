package com.example.mvc.controller;


import com.example.mvc.dto.TestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "CodeController", description = "코드 엔드포인트")
@RequestMapping("/code")
@RestController
public class CodeController {

    @Operation(summary = "01. 코드 조회", description = "코드 조회합니다.")
    @GetMapping("/test")
    public String test() {
        return "tt";
    }

    @Operation(summary = "02. 코드 조회", description = "코드  조회합니다.")
    @GetMapping("/failover")
    public TestDto test2() {
        return new TestDto("dd");
    }

}
