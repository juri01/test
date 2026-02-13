package com.example.webfluxelastic.payment.dto.provider;

/**
 * 제휴사별 취소(Cancel) 고유 파라미터.
 */
public sealed interface ProviderCancelParams {

    record KakaoCancelParams(
            String cid
    ) implements ProviderCancelParams {}

    record NaverCancelParams(
            String cancelReason
    ) implements ProviderCancelParams {}
}
