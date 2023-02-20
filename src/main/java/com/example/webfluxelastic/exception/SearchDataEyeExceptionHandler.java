package com.example.webfluxelastic.exception;

import com.example.webfluxelastic.common.Constant;
import com.example.webfluxelastic.dto.ApiResponse;
import com.example.webfluxelastic.dto.RequestLogging;
import com.example.webfluxelastic.type.ResponseType;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

@RestControllerAdvice
@Slf4j
public class SearchDataEyeExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    ApiResponse businessExceptionHandler(BusinessException ex, ServerWebExchange exchange) {
        log.info("businessExceptionHandler::" + ex);
        exchange.getResponse().setStatusCode(ex.getResponseType().getStatus());
        return ApiResponse.fail(ResponseType.NOT_FOUND,exchange.getAttribute(Constant.REQUEST_INFO));
    }
/*
    @ExceptionHandler(ReadTimeoutException.class)
    @ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
    ApiResponse readTimeoutExceptionHandler(ReadTimeoutException ex, ServerWebExchange exchange) {
        log.info("readTimeoutExceptionHandler::" + ex);
        return ApiResponse.fail(ResponseType.NOT_FOUND);
    }*/
}
