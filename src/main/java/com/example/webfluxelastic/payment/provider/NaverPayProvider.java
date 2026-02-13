package com.example.webfluxelastic.payment.provider;

import com.example.webfluxelastic.payment.dto.*;
import com.example.webfluxelastic.payment.dto.provider.ProviderApproveParams.NaverApproveParams;
import com.example.webfluxelastic.payment.dto.provider.ProviderAuthParams.NaverAuthParams;
import com.example.webfluxelastic.payment.dto.provider.ProviderCancelParams.NaverCancelParams;
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
public class NaverPayProvider extends AbstractPaymentProvider {

    private final RestTemplate restTemplate;

    @Override
    public PaymentProviderType getProviderType() {
        return PaymentProviderType.NAVER_PAY;
    }

    @Override
    protected PaymentAuthResponse doAuthenticate(PaymentAuthRequest request) {
        if (!(request.providerParams() instanceof NaverAuthParams naverParams)) {
            throw new PaymentException(getProviderType(), "INVALID_PARAMS", "네이버페이 인증 파라미터가 필요합니다.");
        }

        NaverReserveResponse res = restTemplate.postForObject(
                "/naverpay/payments/v2/reserve",
                buildNaverReserveBody(request, naverParams),
                NaverReserveResponse.class
        );
        return new PaymentAuthResponse(res.paymentId(), res.redirectUrl());
    }

    @Override
    protected PaymentApproveResponse doApprove(PaymentApproveRequest request) {
        if (!(request.providerParams() instanceof NaverApproveParams naverParams)) {
            throw new PaymentException(getProviderType(), "INVALID_PARAMS", "네이버페이 승인 파라미터가 필요합니다.");
        }

        NaverApproveResponse res = restTemplate.postForObject(
                "/naverpay/payments/v2/apply/payment",
                buildNaverApproveBody(naverParams),
                NaverApproveResponse.class
        );
        return new PaymentApproveResponse(
                request.transactionId(),
                res.merchantPayKey(),
                res.totalPayAmount(),
                res.taxFreeAmount(),
                res.paymentId(),
                res.admissionYmdt()
        );
    }

    @Override
    protected PaymentCancelResponse doCancel(PaymentCancelRequest request) {
        if (!(request.providerParams() instanceof NaverCancelParams naverParams)) {
            throw new PaymentException(getProviderType(), "INVALID_PARAMS", "네이버페이 취소 파라미터가 필요합니다.");
        }

        NaverCancelResponse res = restTemplate.postForObject(
                "/naverpay/payments/v2/cancel",
                buildNaverCancelBody(request, naverParams),
                NaverCancelResponse.class
        );
        return new PaymentCancelResponse(
                request.transactionId(),
                res.merchantPayKey(),
                res.cancelAmount(),
                res.remainAmount(),
                res.cancelledYmdt()
        );
    }

    // ── 네이버페이 전용 요청 빌더 ────────────────────────────────

    private NaverReserveBody buildNaverReserveBody(PaymentAuthRequest request, NaverAuthParams params) {
        return new NaverReserveBody(
                request.orderId(),
                params.merchantUserKey(),
                request.orderName(),
                params.productCategory(),
                request.totalAmount().intValue(),
                request.taxFreeAmount().intValue(),
                request.returnUrl()
        );
    }

    private NaverApproveBody buildNaverApproveBody(NaverApproveParams params) {
        return new NaverApproveBody(params.paymentId());
    }

    private NaverCancelBody buildNaverCancelBody(PaymentCancelRequest request, NaverCancelParams params) {
        return new NaverCancelBody(
                request.transactionId(),
                request.cancelAmount().intValue(),
                request.cancelTaxFreeAmount().intValue(),
                params.cancelReason()
        );
    }

    // ── 네이버페이 전용 내부 DTO ─────────────────────────────────

    private record NaverReserveBody(String merchantPayKey, String merchantUserKey,
                                    String productName, String productCategory,
                                    int totalPayAmount, int taxFreeAmount,
                                    String returnUrl) {}

    private record NaverApproveBody(String paymentId) {}

    private record NaverCancelBody(String paymentId, int cancelAmount,
                                   int cancelTaxFreeAmount, String cancelReason) {}

    private record NaverReserveResponse(String paymentId, String redirectUrl) {}

    private record NaverApproveResponse(String paymentId, String merchantPayKey,
                                        BigDecimal totalPayAmount, BigDecimal taxFreeAmount,
                                        LocalDateTime admissionYmdt) {}

    private record NaverCancelResponse(String merchantPayKey,
                                       BigDecimal cancelAmount, BigDecimal remainAmount,
                                       LocalDateTime cancelledYmdt) {}
}
