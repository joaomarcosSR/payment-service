package com.payment.service.application.consumer.service;

import com.payment.service.domain.consumer.Consumer;
import com.payment.service.domain.consumer.ConsumerGateway;

import java.util.Optional;


public class DefaultConsumerService implements ConsumerService {

    private final ConsumerGateway consumerGateway;
    //TODO: adicionar um cash, essa cara vai ser request scope.

    public DefaultConsumerService(final ConsumerGateway consumerGateway) {
        this.consumerGateway = consumerGateway;
    }

    @Override
    public Optional<Consumer> getConsumer(final String token) {
        return this.consumerGateway.findByToken(token);
    }
}
