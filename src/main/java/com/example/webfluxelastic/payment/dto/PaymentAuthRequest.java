package com.example.webfluxelastic.payment.dto;

import com.example.webfluxelastic.payment.type.PaymentProviderType;

import java.math.BigDecimal;

public record PaymentAuthRequest(
        PaymentProviderType providerType,
        String orderId,
        String orderName,
        BigDecimal totalAmount,
        BigDecimal taxFreeAmount,
        String returnUrl,
        String cancelUrl
) {
}
