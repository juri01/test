package com.example.webfluxelastic.payment.provider;

import com.example.webfluxelastic.payment.dto.*;
import com.example.webfluxelastic.payment.type.PaymentProviderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoPayProvider extends AbstractPaymentProvider {

    private final RestTemplate restTemplate;

    @Override
    public PaymentProviderType getProviderType() {
        return PaymentProviderType.KAKAO_PAY;
    }

    @Override
    protected PaymentAuthResponse doAuthenticate(PaymentAuthRequest request) {
        // TODO: 실제 카카오페이 Ready API 호출로 교체
        // POST https://open-api.kakaopay.com/online/v1/payment/ready
        KakaoReadyResponse res = restTemplate.postForObject(
                "/kakaopay/v1/payment/ready",
                buildKakaoAuthBody(request),
                KakaoReadyResponse.class
        );
        return new PaymentAuthResponse(res.tid(), res.nextRedirectPcUrl());
    }

    @Override
    protected PaymentApproveResponse doApprove(PaymentApproveRequest request) {
        // TODO: 실제 카카오페이 Approve API 호출로 교체
        // POST https://open-api.kakaopay.com/online/v1/payment/approve
        KakaoApproveResponse res = restTemplate.postForObject(
                "/kakaopay/v1/payment/approve",
                buildKakaoApproveBody(request),
                KakaoApproveResponse.class
        );
        return new PaymentApproveResponse(
                request.transactionId(),
                res.partnerOrderId(),
                res.amount(),
                res.taxFreeAmount(),
                res.aid(),
                res.approvedAt()
        );
    }

    @Override
    protected PaymentCancelResponse doCancel(PaymentCancelRequest request) {
        // TODO: 실제 카카오페이 Cancel API 호출로 교체
        // POST https://open-api.kakaopay.com/online/v1/payment/cancel
        KakaoCancelResponse res = restTemplate.postForObject(
                "/kakaopay/v1/payment/cancel",
                buildKakaoCancelBody(request),
                KakaoCancelResponse.class
        );
        return new PaymentCancelResponse(
                request.transactionId(),
                res.partnerOrderId(),
                res.cancelAmount(),
                res.remainAmount(),
                res.cancelledAt()
        );
    }

    // ── 카카오페이 전용 요청 빌더 ────────────────────────────────

    private KakaoReadyBody buildKakaoAuthBody(PaymentAuthRequest request) {
        return new KakaoReadyBody(
                request.orderId(),
                request.orderName(),
                request.totalAmount().intValue(),
                request.taxFreeAmount().intValue(),
                request.returnUrl(),
                request.cancelUrl()
        );
    }

    private KakaoApproveBody buildKakaoApproveBody(PaymentApproveRequest request) {
        return new KakaoApproveBody(request.transactionId(), request.pgToken());
    }

    private KakaoCancelBody buildKakaoCancelBody(PaymentCancelRequest request) {
        return new KakaoCancelBody(
                request.transactionId(),
                request.cancelAmount().intValue(),
                request.cancelTaxFreeAmount().intValue()
        );
    }

    // ── 카카오페이 전용 내부 DTO ─────────────────────────────────

    private record KakaoReadyBody(String partnerOrderId, String itemName,
                                  int totalAmount, int taxFreeAmount,
                                  String approvalUrl, String cancelUrl) {}

    private record KakaoApproveBody(String tid, String pgToken) {}

    private record KakaoCancelBody(String tid, int cancelAmount, int cancelTaxFreeAmount) {}

    private record KakaoReadyResponse(String tid, String nextRedirectPcUrl) {}

    private record KakaoApproveResponse(String aid, String partnerOrderId,
                                        BigDecimal amount, BigDecimal taxFreeAmount,
                                        LocalDateTime approvedAt) {}

    private record KakaoCancelResponse(String partnerOrderId,
                                       BigDecimal cancelAmount, BigDecimal remainAmount,
                                       LocalDateTime cancelledAt) {}
}
