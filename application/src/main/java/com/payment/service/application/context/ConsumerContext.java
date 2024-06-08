package com.payment.service.application.context;

import com.payment.service.domain.consumer.Consumer;

public class ConsumerContext {
    private static final ThreadLocal<Consumer> consumerHolder = new ThreadLocal<>();

    public static void setConsumer(final Consumer consumer) {
        consumerHolder.set(consumer);
    }

    public static Consumer getConsumer() {
        return consumerHolder.get();
    }

    public static void clear() {
        consumerHolder.remove();
    }
}
