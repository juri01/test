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
public class NaverPayProvider extends AbstractPaymentProvider {

    private final WebClient webClient;

    @Override
    public PaymentProviderType getProviderType() {
        return PaymentProviderType.NAVER_PAY;
    }

    @Override
    protected Mono<PaymentAuthResponse> doAuthenticate(PaymentAuthRequest request) {
        // TODO: 실제 네이버페이 결제 예약 API 호출로 교체
        // POST https://dev.apis.naver.com/naverpay-partner/naverpay/payments/v2/reserve
        return webClient.post()
                .uri("/naverpay/payments/v2/reserve")
                .bodyValue(buildNaverReserveBody(request))
                .retrieve()
                .bodyToMono(NaverReserveResponse.class)
                .map(res -> new PaymentAuthResponse(res.paymentId(), res.redirectUrl()));
    }

    @Override
    protected Mono<PaymentApproveResponse> doApprove(PaymentApproveRequest request) {
        // TODO: 실제 네이버페이 결제 승인 API 호출로 교체
        // POST https://dev.apis.naver.com/naverpay-partner/naverpay/payments/v2/apply/payment
        return webClient.post()
                .uri("/naverpay/payments/v2/apply/payment")
                .bodyValue(buildNaverApproveBody(request))
                .retrieve()
                .bodyToMono(NaverApproveResponse.class)
                .map(res -> new PaymentApproveResponse(
                        request.transactionId(),
                        res.merchantPayKey(),
                        res.totalPayAmount(),
                        res.taxFreeAmount(),
                        res.paymentId(),
                        res.admissionYmdt()
                ));
    }

    @Override
    protected Mono<PaymentCancelResponse> doCancel(PaymentCancelRequest request) {
        // TODO: 실제 네이버페이 결제 취소 API 호출로 교체
        // POST https://dev.apis.naver.com/naverpay-partner/naverpay/payments/v2/cancel
        return webClient.post()
                .uri("/naverpay/payments/v2/cancel")
                .bodyValue(buildNaverCancelBody(request))
                .retrieve()
                .bodyToMono(NaverCancelResponse.class)
                .map(res -> new PaymentCancelResponse(
                        request.transactionId(),
                        res.merchantPayKey(),
                        res.cancelAmount(),
                        res.remainAmount(),
                        res.cancelledYmdt()
                ));
    }

    // ── 네이버페이 전용 요청 빌더 ────────────────────────────────

    private NaverReserveBody buildNaverReserveBody(PaymentAuthRequest request) {
        return new NaverReserveBody(
                request.orderId(),
                request.orderName(),
                request.totalAmount().intValue(),
                request.taxFreeAmount().intValue(),
                request.returnUrl()
        );
    }

    private NaverApproveBody buildNaverApproveBody(PaymentApproveRequest request) {
        return new NaverApproveBody(request.transactionId());
    }

    private NaverCancelBody buildNaverCancelBody(PaymentCancelRequest request) {
        return new NaverCancelBody(
                request.transactionId(),
                request.cancelAmount().intValue(),
                request.cancelTaxFreeAmount().intValue(),
                request.cancelReason()
        );
    }

    // ── 네이버페이 전용 내부 DTO ─────────────────────────────────

    private record NaverReserveBody(String merchantPayKey, String productName,
                                    int totalPayAmount, int taxFreeAmount,
                                    String returnUrl) {}

    private record NaverApproveBody(String paymentId) {}

    private record NaverCancelBody(String paymentId, int cancelAmount,
                                   int cancelTaxFreeAmount, String cancelReason) {}

    private record NaverReserveResponse(String paymentId, String redirectUrl) {}

    private record NaverApproveResponse(String paymentId, String merchantPayKey,
                                        java.math.BigDecimal totalPayAmount, java.math.BigDecimal taxFreeAmount,
                                        LocalDateTime admissionYmdt) {}

    private record NaverCancelResponse(String merchantPayKey,
                                       java.math.BigDecimal cancelAmount, java.math.BigDecimal remainAmount,
                                       LocalDateTime cancelledYmdt) {}
}
