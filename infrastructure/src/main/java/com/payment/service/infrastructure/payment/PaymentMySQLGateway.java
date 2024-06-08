package com.payment.service.infrastructure.payment;

import com.payment.service.domain.pagination.Pagination;
import com.payment.service.domain.payment.Payment;
import com.payment.service.domain.payment.PaymentGateway;
import com.payment.service.domain.payment.PaymentID;
import com.payment.service.domain.payment.PaymentSearchQuery;
import com.payment.service.infrastructure.notifier.ConsumerNotifier;
import com.payment.service.infrastructure.payment.persistence.PaymentJpaEntity;
import com.payment.service.infrastructure.payment.persistence.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentMySQLGateway implements PaymentGateway {
    private final PaymentRepository paymentRepository;
    private final ConsumerNotifier consumerNotifier;

    public PaymentMySQLGateway(final PaymentRepository paymentRepository, final ConsumerNotifier consumerNotifier) {
        this.paymentRepository = paymentRepository;
        this.consumerNotifier = consumerNotifier;
    }

    @Override
    public Payment create(final Payment aPayment) {
        return this.paymentRepository.save(PaymentJpaEntity.from(aPayment)).toAggregate();
    }

    @Override
    public Optional<Payment> findById(final PaymentID anId) {
        return this.paymentRepository.findById(anId.getValue())
                .map(PaymentJpaEntity::toAggregate);
    }

    @Override
    public Payment update(final Payment aPayment) {
        final Payment payment = this.paymentRepository.save(PaymentJpaEntity.from(aPayment)).toAggregate();
        aPayment.publishDomainEvents(event -> this.consumerNotifier.notify(event, payment.getConsumerId().getValue()));
        return payment;
    }

    @Override
    public Pagination<Payment> findAll(final PaymentSearchQuery aQuery) {
        return null;
    }
}
