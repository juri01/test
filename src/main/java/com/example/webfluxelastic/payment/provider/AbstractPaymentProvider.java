package com.example.webfluxelastic.payment.provider;

import com.example.webfluxelastic.payment.dto.*;
import com.example.webfluxelastic.payment.exception.PaymentException;
import lombok.extern.slf4j.Slf4j;

/**
 * Template Method 패턴을 적용한 결제 제휴사 추상 클래스.
 *
 * 공통 흐름(로깅, 에러 처리, 검증)은 이 클래스에서 처리하고,
 * 각 제휴사별 실제 API 호출 로직은 하위 클래스에서 구현한다.
 */
@Slf4j
public abstract non-sealed class AbstractPaymentProvider implements PaymentProvider {

    // ── 공통 흐름: 인증 ──────────────────────────────────────────

    @Override
    public final PaymentAuthResponse authenticate(PaymentAuthRequest request) {
        try {
            log.info("[{}] 결제 인증 요청 - orderId: {}, amount: {}",
                    getProviderType().getDisplayName(), request.orderId(), request.totalAmount());
            validateAuthRequest(request);
            PaymentAuthResponse response = doAuthenticate(request);
            log.info("[{}] 결제 인증 성공 - transactionId: {}",
                    getProviderType().getDisplayName(), response.transactionId());
            return response;
        } catch (PaymentException e) {
            throw e;
        } catch (Exception e) {
            log.error("[{}] 결제 인증 실패 - orderId: {}", getProviderType().getDisplayName(), request.orderId(), e);
            throw new PaymentException(getProviderType(), "AUTH_ERROR", "인증 처리 중 오류 발생: " + e.getMessage(), e);
        }
    }

    // ── 공통 흐름: 승인 ──────────────────────────────────────────

    @Override
    public final PaymentApproveResponse approve(PaymentApproveRequest request) {
        try {
            log.info("[{}] 결제 승인 요청 - transactionId: {}",
                    getProviderType().getDisplayName(), request.transactionId());
            validateApproveRequest(request);
            PaymentApproveResponse response = doApprove(request);
            log.info("[{}] 결제 승인 성공 - transactionId: {}, amount: {}",
                    getProviderType().getDisplayName(), response.transactionId(), response.totalAmount());
            return response;
        } catch (PaymentException e) {
            throw e;
        } catch (Exception e) {
            log.error("[{}] 결제 승인 실패 - transactionId: {}", getProviderType().getDisplayName(), request.transactionId(), e);
            throw new PaymentException(getProviderType(), "APPROVE_ERROR", "승인 처리 중 오류 발생: " + e.getMessage(), e);
        }
    }

    // ── 공통 흐름: 취소 ──────────────────────────────────────────

    @Override
    public final PaymentCancelResponse cancel(PaymentCancelRequest request) {
        try {
            log.info("[{}] 결제 취소 요청 - transactionId: {}, cancelAmount: {}",
                    getProviderType().getDisplayName(), request.transactionId(), request.cancelAmount());
            validateCancelRequest(request);
            PaymentCancelResponse response = doCancel(request);
            log.info("[{}] 결제 취소 성공 - transactionId: {}, cancelledAmount: {}",
                    getProviderType().getDisplayName(), response.transactionId(), response.cancelledAmount());
            return response;
        } catch (PaymentException e) {
            throw e;
        } catch (Exception e) {
            log.error("[{}] 결제 취소 실패 - transactionId: {}", getProviderType().getDisplayName(), request.transactionId(), e);
            throw new PaymentException(getProviderType(), "CANCEL_ERROR", "취소 처리 중 오류 발생: " + e.getMessage(), e);
        }
    }

    // ── 제휴사별 구현 필요 (Template Method) ─────────────────────

    protected abstract PaymentAuthResponse doAuthenticate(PaymentAuthRequest request);

    protected abstract PaymentApproveResponse doApprove(PaymentApproveRequest request);

    protected abstract PaymentCancelResponse doCancel(PaymentCancelRequest request);

    // ── 검증 로직 (하위 클래스에서 오버라이드 가능) ──────────────

    protected void validateAuthRequest(PaymentAuthRequest request) {
        if (request.orderId() == null || request.orderId().isBlank()) {
            throw new PaymentException(getProviderType(), "INVALID_REQUEST", "주문 ID는 필수입니다.");
        }
        if (request.totalAmount() == null || request.totalAmount().signum() <= 0) {
            throw new PaymentException(getProviderType(), "INVALID_REQUEST", "결제 금액은 0보다 커야 합니다.");
        }
    }

    protected void validateApproveRequest(PaymentApproveRequest request) {
        if (request.transactionId() == null || request.transactionId().isBlank()) {
            throw new PaymentException(getProviderType(), "INVALID_REQUEST", "거래 ID는 필수입니다.");
        }
    }

    protected void validateCancelRequest(PaymentCancelRequest request) {
        if (request.transactionId() == null || request.transactionId().isBlank()) {
            throw new PaymentException(getProviderType(), "INVALID_REQUEST", "거래 ID는 필수입니다.");
        }
        if (request.cancelAmount() == null || request.cancelAmount().signum() <= 0) {
            throw new PaymentException(getProviderType(), "INVALID_REQUEST", "취소 금액은 0보다 커야 합니다.");
        }
    }
}
