package com.payment.service.infrastructure.consumer;

import com.payment.service.domain.consumer.Consumer;
import com.payment.service.domain.consumer.ConsumerGateway;
import com.payment.service.domain.consumer.ConsumerID;
import com.payment.service.domain.consumer.ConsumerSearchQuery;
import com.payment.service.domain.pagination.Pagination;
import com.payment.service.domain.payment.PaymentID;
import com.payment.service.infrastructure.consumer.persistence.ConsumerJpaEntity;
import com.payment.service.infrastructure.consumer.persistence.ConsumerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsumerMySQLGateway implements ConsumerGateway {
    private final ConsumerRepository consumerRepository;

    public ConsumerMySQLGateway(final ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }

    @Override
    public Consumer create(final Consumer aConsumer) {
        return null;
    }

    @Override
    public Optional<Consumer> findById(final PaymentID anId) {
        return Optional.empty();
    }

    @Override
    public Optional<Consumer> findByToken(final String token) {
        return this.consumerRepository.findByToken(token)
                .map(ConsumerJpaEntity::toAggregation);
    }

    @Override
    public Consumer update(final Consumer aConsumer) {
        return null;
    }

    @Override
    public Pagination<Consumer> findAll(final ConsumerSearchQuery aQuery) {
        return null;
    }

    @Override
    public boolean delete(final ConsumerID anId) {
        return false;
    }
}
