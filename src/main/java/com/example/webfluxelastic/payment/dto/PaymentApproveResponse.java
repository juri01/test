package com.example.webfluxelastic.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentApproveResponse(
        String transactionId,
        String orderId,
        BigDecimal totalAmount,
        BigDecimal taxFreeAmount,
        String providerTransactionId,
        LocalDateTime approvedAt
) {
}
