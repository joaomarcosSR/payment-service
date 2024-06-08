package com.payment.service.domain.payment;

import com.payment.service.domain.pagination.Pagination;

import java.util.Optional;

public interface PaymentGateway {
    Payment create(Payment aPayment);

    Optional<Payment> findById(PaymentID anId);

    Payment update(Payment aPayment);

    Pagination<Payment> findAll(PaymentSearchQuery aQuery);
}
