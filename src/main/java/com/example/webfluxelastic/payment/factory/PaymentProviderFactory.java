package com.example.webfluxelastic.payment.factory;

import com.example.webfluxelastic.payment.provider.PaymentProvider;
import com.example.webfluxelastic.payment.type.PaymentProviderType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Spring DI를 활용한 결제 제휴사 라우팅 팩토리.
 * 모든 PaymentProvider 빈을 주입받아 providerType 기준으로 라우팅한다.
 */
@Component
public class PaymentProviderFactory {

    private final Map<PaymentProviderType, PaymentProvider> providerMap;

    public PaymentProviderFactory(List<PaymentProvider> providers) {
        this.providerMap = new EnumMap<>(PaymentProviderType.class);
        providers.forEach(provider -> providerMap.put(provider.getProviderType(), provider));
    }

    public PaymentProvider getProvider(PaymentProviderType type) {
        PaymentProvider provider = providerMap.get(type);
        if (provider == null) {
            throw new IllegalArgumentException("등록되지 않은 결제 제휴사: " + type.getDisplayName());
        }
        return provider;
    }
}
