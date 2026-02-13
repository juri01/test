package com.example.webfluxelastic.payment.dto.provider;

/**
 * 제휴사별 인증(Ready) 고유 파라미터.
 * 새 제휴사 추가 시 여기에 record를 추가한다.
 */
public sealed interface ProviderAuthParams {

    record KakaoAuthParams(
            String cid,
            String partnerUserId
    ) implements ProviderAuthParams {}

    record NaverAuthParams(
            String merchantUserKey,
            String productCategory
    ) implements ProviderAuthParams {}
}
