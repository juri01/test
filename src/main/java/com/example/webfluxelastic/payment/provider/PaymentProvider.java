package com.example.webfluxelastic.payment.provider;

import com.example.webfluxelastic.payment.dto.*;
import com.example.webfluxelastic.payment.type.PaymentProviderType;

/**
 * 결제 제휴사 공통 인터페이스.
 * 모든 제휴사(카카오, 네이버 등)가 구현해야 하는 계약을 정의한다.
 */
public sealed interface PaymentProvider
        permits AbstractPaymentProvider {

    PaymentProviderType getProviderType();

    PaymentAuthResponse authenticate(PaymentAuthRequest request);

    PaymentApproveResponse approve(PaymentApproveRequest request);

    PaymentCancelResponse cancel(PaymentCancelRequest request);
}
