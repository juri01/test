package com.example.webfluxelastic.payment.exception;

import com.example.webfluxelastic.payment.type.PaymentProviderType;
import lombok.Getter;

@Getter
public class PaymentException extends RuntimeException {

    private final PaymentProviderType providerType;
    private final String errorCode;

    public PaymentException(PaymentProviderType providerType, String errorCode, String message) {
        super(message);
        this.providerType = providerType;
        this.errorCode = errorCode;
    }

    public PaymentException(PaymentProviderType providerType, String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.providerType = providerType;
        this.errorCode = errorCode;
    }
}
