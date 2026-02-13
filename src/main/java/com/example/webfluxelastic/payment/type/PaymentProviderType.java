package com.example.webfluxelastic.payment.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentProviderType {

    KAKAO_PAY("kakaopay", "카카오페이"),
    NAVER_PAY("naverpay", "네이버페이");

    private final String code;
    private final String displayName;

    public static PaymentProviderType fromCode(String code) {
        for (PaymentProviderType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("지원하지 않는 결제 제휴사 코드: " + code);
    }
}
