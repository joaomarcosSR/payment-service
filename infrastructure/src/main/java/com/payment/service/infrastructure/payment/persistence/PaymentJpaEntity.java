package com.payment.service.infrastructure.payment.persistence;

import com.payment.service.domain.consumer.ConsumerID;
import com.payment.service.domain.payment.Payment;
import com.payment.service.domain.payment.PaymentID;
import com.payment.service.domain.payment.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity(name = "payment")
@Table(name = "Payments")
public class PaymentJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "consumer_id", nullable = false)
    private String consumerId;
    @Column(name = "payment_key", nullable = false)
    private String paymentKey;
    @Column(name = "status", length = 30, nullable = false)
    private String status;
    @Column(name = "error_message", length = 4000)
    private String errorMessage;
    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updatedAt;

    public PaymentJpaEntity() {

    }

    private PaymentJpaEntity(
            final String id,
            final String consumerId,
            final String paymentKey,
            final String status,
            final String errorMessage,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        this.id = id;
        this.consumerId = consumerId;
        this.paymentKey = paymentKey;
        this.status = status;
        this.errorMessage = errorMessage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static PaymentJpaEntity from(final Payment aPayment) {
        return new PaymentJpaEntity(
                aPayment.getId().getValue(),
                aPayment.getConsumerId().getValue(),
                aPayment.getPaymentKey(),
                aPayment.getStatus().name(),
                aPayment.getErrorMessage(),
                aPayment.getCreatedAt(),
                aPayment.getUpdatedAt()
        );
    }

    public Payment toAggregate() {
        return Payment.with(
                PaymentID.from(getId()),
                ConsumerID.from(getConsumerId()),
                getPaymentKey(),
                PaymentStatus.valueOf(getStatus()),
                getErrorMessage(),
                getCreatedAt(),
                getUpdatedAt()
        );
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getConsumerId() {
        return this.consumerId;
    }

    public void setConsumerId(final String consumerId) {
        this.consumerId = consumerId;
    }

    public String getPaymentKey() {
        return this.paymentKey;
    }

    public void setPaymentKey(final String paymentKey) {
        this.paymentKey = paymentKey;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(final Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(final Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
