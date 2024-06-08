package com.payment.service.infrastructure.consumer.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsumerRepository extends JpaRepository<ConsumerJpaEntity, String> {
    Optional<ConsumerJpaEntity> findByToken(String token);
}
