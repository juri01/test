package com.example.webfluxelastic.dto;

import com.example.webfluxelastic.common.Constant;
import com.example.webfluxelastic.type.ResponseType;
import lombok.Generated;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalDateTime;

@Getter
@ToString
@Generated
public class ApiResponse<T> {
    private final int status;
    private final String code;
    private final String message;
    private final T data;
    private final String requestId;
    private final LocalDateTime requestAt;
    private final LocalDateTime responseAt;

    public ApiResponse(ResponseType responseType, T data, String message) {
        this.status = responseType.getStatus().value();
        this.code = responseType.getCode();
        this.message = message;
        this.data = data;
        this.requestId = "";
        this.requestAt = LocalDateTime.now();
        this.responseAt = LocalDateTime.now();
    }
    public ApiResponse(ResponseType responseType, T data, String message, RequestLogging requestLogging) {
        this.status = responseType.getStatus().value();
        this.code = responseType.getCode();
        this.message = message;
        this.data = data;
        this.requestId = requestLogging.getUuid();
        this.requestAt = requestLogging.getRequestAt();
        this.responseAt = LocalDateTime.now();
    }
    public static<T> ApiResponse<T> ok(T data) {
        return of(ResponseType.OK, data, ResponseType.OK.getMessage());
    }
    public static<T> ApiResponse<T> ok(RequestLogging requestLogging, T data) {
        return of(ResponseType.OK, data, ResponseType.OK.getMessage(), requestLogging);
    }
    private static <T> ApiResponse<T> of(ResponseType responseType, T data, String message, RequestLogging requestLogging) {
        return new ApiResponse<>(responseType, data, message, requestLogging);
    }
    private static <T> ApiResponse<T> of(ResponseType responseType, T data, String message) {
        return new ApiResponse<>(responseType, data, message);
    }
    public static<T> ApiResponse<T> fail(ResponseType responseType) {
        return of(responseType, null, responseType.getMessage());
    }
    public static<T> ApiResponse<T> fail(ResponseType responseType, RequestLogging requestLogging) {
        return of(responseType, null, responseType.getMessage(), requestLogging);
    }
    private static ApiResponse<Void> fail(ResponseType responseType, String message) {
        return of(responseType, null, message);
    }
}
