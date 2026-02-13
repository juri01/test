package com.example.webfluxelastic.payment.dto.provider;

/**
 * 제휴사별 승인(Approve) 고유 파라미터.
 */
public sealed interface ProviderApproveParams {

    record KakaoApproveParams(
            String cid,
            String partnerOrderId,
            String partnerUserId,
            String pgToken
    ) implements ProviderApproveParams {}

    record NaverApproveParams(
            String paymentId
    ) implements ProviderApproveParams {}
}
