package com.payment.service.infrastructure.payment.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentJpaEntity, String> {
}
