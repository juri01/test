package com.example.webfluxelastic.config;

import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import org.slf4j.MDC;
import reactor.core.CoreSubscriber;
import reactor.util.context.Context;

/** Helper that copies the state of Reactor [Context] to MDC on the #onNext function. */
@Slf4j
@RequiredArgsConstructor
public class MdcContextLifter<T> implements CoreSubscriber<T> {

    private final CoreSubscriber<T> coreSubscriber;

    @Override
    public void onSubscribe(Subscription subscription) {
        coreSubscriber.onSubscribe(subscription);
    }

    @Override
    public void onNext(T t) {
        copyToMdc(coreSubscriber.currentContext());
        coreSubscriber.onNext(t);
    }

    @Override
    public void onError(Throwable throwable) {
        coreSubscriber.onError(throwable);
    }

    @Override
    public void onComplete() {
        coreSubscriber.onComplete();
    }

    @Override
    public Context currentContext() {
        return coreSubscriber.currentContext();
    }

    /**
     * Extension function for the Reactor [Context]. Copies the current context to the MDC, if context
     * is empty clears the MDC. State of the MDC after calling this method should be same as Reactor
     * [Context] state. One thread-local access only.
     */
    void copyToMdc(Context context) {
        if (context != null && !context.isEmpty()) {
            Map<String, String> map =
                    context.stream()
                            .collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString()));

            MDC.setContextMap(map);

        } else {
            MDC.clear();
        }
    }

}
