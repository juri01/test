package com.example.webfluxelastic.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentCancelResponse(
        String transactionId,
        String orderId,
        BigDecimal cancelledAmount,
        BigDecimal remainingAmount,
        LocalDateTime cancelledAt
) {
}
