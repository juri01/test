package com.example.webfluxelastic.payment.dto;

import com.example.webfluxelastic.payment.dto.provider.ProviderApproveParams;
import com.example.webfluxelastic.payment.type.PaymentProviderType;

public record PaymentApproveRequest(
        PaymentProviderType providerType,
        String transactionId,
        ProviderApproveParams providerParams
) {
}
