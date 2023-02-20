package com.example.webfluxelastic.exception;

import com.example.webfluxelastic.type.ResponseType;
import lombok.Getter;

public class BusinessException extends RuntimeException {
    @Getter
    private final ResponseType responseType;

    public BusinessException(String message, ResponseType responseType) {
        super(message);
        this.responseType = responseType;
    }

    public BusinessException(ResponseType responseType, Object... args) {
        this(String.format(responseType.getMessage(), args), responseType);
    }
}
