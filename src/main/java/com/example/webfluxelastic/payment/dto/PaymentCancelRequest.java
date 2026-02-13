package com.example.webfluxelastic.payment.dto;

import com.example.webfluxelastic.payment.type.PaymentProviderType;

import java.math.BigDecimal;

public record PaymentCancelRequest(
        PaymentProviderType providerType,
        String transactionId,
        BigDecimal cancelAmount,
        BigDecimal cancelTaxFreeAmount,
        String cancelReason
) {
}
