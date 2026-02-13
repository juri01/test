package com.example.webfluxelastic.payment.provider;

import com.example.webfluxelastic.payment.dto.*;
import com.example.webfluxelastic.payment.type.PaymentProviderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoPayProvider extends AbstractPaymentProvider {

    private final WebClient webClient;

    @Override
    public PaymentProviderType getProviderType() {
        return PaymentProviderType.KAKAO_PAY;
    }

    @Override
    protected Mono<PaymentAuthResponse> doAuthenticate(PaymentAuthRequest request) {
        // TODO: 실제 카카오페이 Ready API 호출로 교체
        // POST https://open-api.kakaopay.com/online/v1/payment/ready
        return webClient.post()
                .uri("/kakaopay/v1/payment/ready")
                .bodyValue(buildKakaoAuthBody(request))
                .retrieve()
                .bodyToMono(KakaoReadyResponse.class)
                .map(res -> new PaymentAuthResponse(res.tid(), res.nextRedirectPcUrl()));
    }

    @Override
    protected Mono<PaymentApproveResponse> doApprove(PaymentApproveRequest request) {
        // TODO: 실제 카카오페이 Approve API 호출로 교체
        // POST https://open-api.kakaopay.com/online/v1/payment/approve
        return webClient.post()
                .uri("/kakaopay/v1/payment/approve")
                .bodyValue(buildKakaoApproveBody(request))
                .retrieve()
                .bodyToMono(KakaoApproveResponse.class)
                .map(res -> new PaymentApproveResponse(
                        request.transactionId(),
                        res.partnerOrderId(),
                        res.amount(),
                        res.taxFreeAmount(),
                        res.aid(),
                        res.approvedAt()
                ));
    }

    @Override
    protected Mono<PaymentCancelResponse> doCancel(PaymentCancelRequest request) {
        // TODO: 실제 카카오페이 Cancel API 호출로 교체
        // POST https://open-api.kakaopay.com/online/v1/payment/cancel
        return webClient.post()
                .uri("/kakaopay/v1/payment/cancel")
                .bodyValue(buildKakaoCancelBody(request))
                .retrieve()
                .bodyToMono(KakaoCancelResponse.class)
                .map(res -> new PaymentCancelResponse(
                        request.transactionId(),
                        res.partnerOrderId(),
                        res.cancelAmount(),
                        res.remainAmount(),
                        res.cancelledAt()
                ));
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
                                        java.math.BigDecimal amount, java.math.BigDecimal taxFreeAmount,
                                        LocalDateTime approvedAt) {}

    private record KakaoCancelResponse(String partnerOrderId,
                                       java.math.BigDecimal cancelAmount, java.math.BigDecimal remainAmount,
                                       LocalDateTime cancelledAt) {}
}
