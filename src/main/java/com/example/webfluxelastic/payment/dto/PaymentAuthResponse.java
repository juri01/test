package com.example.webfluxelastic.payment.dto;

public record PaymentAuthResponse(
        String transactionId,
        String redirectUrl
) {
}
