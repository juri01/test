package com.example.webfluxelastic.payment.provider;

import com.example.webfluxelastic.payment.dto.*;
import com.example.webfluxelastic.payment.dto.provider.ProviderApproveParams.KakaoApproveParams;
import com.example.webfluxelastic.payment.dto.provider.ProviderAuthParams.KakaoAuthParams;
import com.example.webfluxelastic.payment.dto.provider.ProviderCancelParams.KakaoCancelParams;
import com.example.webfluxelastic.payment.exception.PaymentException;
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
        if (!(request.providerParams() instanceof KakaoAuthParams kakaoParams)) {
            throw new PaymentException(getProviderType(), "INVALID_PARAMS", "카카오페이 인증 파라미터가 필요합니다.");
        }

        KakaoReadyResponse res = restTemplate.postForObject(
                "/kakaopay/v1/payment/ready",
                buildKakaoAuthBody(request, kakaoParams),
                KakaoReadyResponse.class
        );
        return new PaymentAuthResponse(res.tid(), res.nextRedirectPcUrl());
    }

    @Override
    protected PaymentApproveResponse doApprove(PaymentApproveRequest request) {
        if (!(request.providerParams() instanceof KakaoApproveParams kakaoParams)) {
            throw new PaymentException(getProviderType(), "INVALID_PARAMS", "카카오페이 승인 파라미터가 필요합니다.");
        }

        KakaoApproveResponse res = restTemplate.postForObject(
                "/kakaopay/v1/payment/approve",
                buildKakaoApproveBody(request, kakaoParams),
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
        if (!(request.providerParams() instanceof KakaoCancelParams kakaoParams)) {
            throw new PaymentException(getProviderType(), "INVALID_PARAMS", "카카오페이 취소 파라미터가 필요합니다.");
        }

        KakaoCancelResponse res = restTemplate.postForObject(
                "/kakaopay/v1/payment/cancel",
                buildKakaoCancelBody(request, kakaoParams),
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

    private KakaoReadyBody buildKakaoAuthBody(PaymentAuthRequest request, KakaoAuthParams params) {
        return new KakaoReadyBody(
                params.cid(),
                request.orderId(),
                params.partnerUserId(),
                request.orderName(),
                request.totalAmount().intValue(),
                request.taxFreeAmount().intValue(),
                request.returnUrl(),
                request.cancelUrl()
        );
    }

    private KakaoApproveBody buildKakaoApproveBody(PaymentApproveRequest request, KakaoApproveParams params) {
        return new KakaoApproveBody(
                params.cid(),
                request.transactionId(),
                params.partnerOrderId(),
                params.partnerUserId(),
                params.pgToken()
        );
    }

    private KakaoCancelBody buildKakaoCancelBody(PaymentCancelRequest request, KakaoCancelParams params) {
        return new KakaoCancelBody(
                params.cid(),
                request.transactionId(),
                request.cancelAmount().intValue(),
                request.cancelTaxFreeAmount().intValue()
        );
    }

    // ── 카카오페이 전용 내부 DTO ─────────────────────────────────

    private record KakaoReadyBody(String cid, String partnerOrderId, String partnerUserId,
                                  String itemName, int totalAmount, int taxFreeAmount,
                                  String approvalUrl, String cancelUrl) {}

    private record KakaoApproveBody(String cid, String tid,
                                    String partnerOrderId, String partnerUserId,
                                    String pgToken) {}

    private record KakaoCancelBody(String cid, String tid,
                                   int cancelAmount, int cancelTaxFreeAmount) {}

    private record KakaoReadyResponse(String tid, String nextRedirectPcUrl) {}

    private record KakaoApproveResponse(String aid, String partnerOrderId,
                                        BigDecimal amount, BigDecimal taxFreeAmount,
                                        LocalDateTime approvedAt) {}

    private record KakaoCancelResponse(String partnerOrderId,
                                       BigDecimal cancelAmount, BigDecimal remainAmount,
                                       LocalDateTime cancelledAt) {}
}
