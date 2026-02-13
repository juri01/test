package com.example.webfluxelastic.payment.dto;

import com.example.webfluxelastic.payment.type.PaymentProviderType;

public record PaymentApproveRequest(
        PaymentProviderType providerType,
        String transactionId,
        String pgToken
) {
}
