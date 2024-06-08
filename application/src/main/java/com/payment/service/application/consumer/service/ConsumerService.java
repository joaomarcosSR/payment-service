package com.payment.service.application.consumer.service;

import com.payment.service.domain.consumer.Consumer;

import java.util.Optional;

//TODO: pode virar use case
public interface ConsumerService {
    Optional<Consumer> getConsumer(String token);
}
