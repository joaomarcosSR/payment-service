package com.payment.service.domain.events;

@FunctionalInterface
public interface EventPublisher {
    void publishEvent(Event event);
}
