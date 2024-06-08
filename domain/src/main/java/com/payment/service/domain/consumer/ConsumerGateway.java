package com.payment.service.domain.consumer;

import com.payment.service.domain.pagination.Pagination;
import com.payment.service.domain.payment.PaymentID;

import java.util.Optional;

public interface ConsumerGateway {
    Consumer create(Consumer aConsumer);

    Optional<Consumer> findById(PaymentID anId);

    Optional<Consumer> findByToken(String token);

    Consumer update(Consumer aConsumer);

    Pagination<Consumer> findAll(ConsumerSearchQuery aQuery);

    boolean delete(ConsumerID anId);
}
