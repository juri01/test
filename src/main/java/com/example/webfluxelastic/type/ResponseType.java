package com.example.webfluxelastic.type;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseType {
    OK(HttpStatus.OK,"200","정상처리"),
    NOT_FOUND(HttpStatus.NOT_FOUND,"400", "페이지가 없습니다.");

    ResponseType(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    private final HttpStatus status;
    private final String code;
    private final String message;
}
