package com.example.webfluxelastic.payment.service;

import com.example.webfluxelastic.payment.dto.*;
import com.example.webfluxelastic.payment.factory.PaymentProviderFactory;
import com.example.webfluxelastic.payment.provider.PaymentProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 결제 Facade 서비스.
 * 외부(Controller 등)에서는 이 서비스만 의존하며, 제휴사 선택은 내부에서 처리된다.
 */
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentProviderFactory providerFactory;

    public Mono<PaymentAuthResponse> authenticate(PaymentAuthRequest request) {
        PaymentProvider provider = providerFactory.getProvider(request.providerType());
        return provider.authenticate(request);
    }

    public Mono<PaymentApproveResponse> approve(PaymentApproveRequest request) {
        PaymentProvider provider = providerFactory.getProvider(request.providerType());
        return provider.approve(request);
    }

    public Mono<PaymentCancelResponse> cancel(PaymentCancelRequest request) {
        PaymentProvider provider = providerFactory.getProvider(request.providerType());
        return provider.cancel(request);
    }
}
