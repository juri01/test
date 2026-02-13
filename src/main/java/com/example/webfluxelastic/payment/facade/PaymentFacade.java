package com.example.webfluxelastic.payment.facade;

import com.example.webfluxelastic.payment.dto.*;
import com.example.webfluxelastic.payment.factory.PaymentProviderFactory;
import com.example.webfluxelastic.payment.provider.PaymentProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 결제 Facade.
 *
 * 내부 로직(DB 조회, 검증, 이력 저장)을 처리한 뒤
 * 제휴사 구간은 Provider에 위임한다.
 *
 * Controller → Facade → Provider
 *                ↕
 *               DB
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentFacade {

    private final PaymentProviderFactory providerFactory;

    // ── 인증 ─────────────────────────────────────────────────────

    public PaymentAuthResponse authenticate(PaymentAuthRequest request) {
        PaymentProvider provider = providerFactory.getProvider(request.providerType());

        validateOrder(request.orderId());
        checkDuplicatePayment(request.orderId());

        PaymentAuthResponse response = provider.authenticate(request);

        savePaymentHistory(request, response);
        return response;
    }

    // ── 승인 ─────────────────────────────────────────────────────

    public PaymentApproveResponse approve(PaymentApproveRequest request) {
        PaymentProvider provider = providerFactory.getProvider(request.providerType());

        findTransaction(request.transactionId());

        PaymentApproveResponse response = provider.approve(request);

        updatePaymentStatus(response);
        return response;
    }

    // ── 취소 ─────────────────────────────────────────────────────

    public PaymentCancelResponse cancel(PaymentCancelRequest request) {
        PaymentProvider provider = providerFactory.getProvider(request.providerType());

        findTransaction(request.transactionId());
        validateCancelable(request.transactionId(), request.cancelAmount());

        PaymentCancelResponse response = provider.cancel(request);

        updateCancelStatus(response);
        return response;
    }

    // ── 내부 로직 (DB 조회, 검증) ────────────────────────────────

    private void validateOrder(String orderId) {
        // TODO: 주문 존재 여부 DB 조회
        log.debug("주문 유효성 검증 - orderId: {}", orderId);
    }

    private void checkDuplicatePayment(String orderId) {
        // TODO: 중복 결제 여부 DB 조회
        log.debug("중복 결제 체크 - orderId: {}", orderId);
    }

    private void findTransaction(String transactionId) {
        // TODO: 거래 존재 여부 DB 조회
        log.debug("거래 조회 - transactionId: {}", transactionId);
    }

    private void validateCancelable(String transactionId, BigDecimal cancelAmount) {
        // TODO: 취소 가능 여부 검증 (이미 취소된 건인지, 금액 초과인지 등)
        log.debug("취소 가능 여부 검증 - transactionId: {}, cancelAmount: {}", transactionId, cancelAmount);
    }

    private void savePaymentHistory(PaymentAuthRequest request, PaymentAuthResponse response) {
        // TODO: 결제 인증 이력 DB 저장
        log.debug("결제 이력 저장 - orderId: {}, transactionId: {}", request.orderId(), response.transactionId());
    }

    private void updatePaymentStatus(PaymentApproveResponse response) {
        // TODO: 결제 승인 상태 DB 업데이트
        log.debug("결제 상태 업데이트 - transactionId: {}", response.transactionId());
    }

    private void updateCancelStatus(PaymentCancelResponse response) {
        // TODO: 취소 상태 DB 업데이트
        log.debug("취소 상태 업데이트 - transactionId: {}", response.transactionId());
    }
}
