package com.payment.service.infrastructure.payment.persistence;

import com.payment.service.domain.consumer.ConsumerID;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "payments_consumers")
public class PaymentConsumerJpaEntity {
    @EmbeddedId
    private PaymentConsumerID id;

    @ManyToOne
    @MapsId("paymentId")
    private PaymentJpaEntity payment;

    public PaymentConsumerJpaEntity() {
    }

    private PaymentConsumerJpaEntity(final PaymentJpaEntity aPayment, final ConsumerID aConsumerId) {
        this.id = PaymentConsumerID.from(aPayment.getId(), aConsumerId.getValue());
        this.payment = aPayment;
    }

    public static PaymentConsumerJpaEntity from(final PaymentJpaEntity aPayment, final ConsumerID aConsumerId) {
        return new PaymentConsumerJpaEntity(aPayment, aConsumerId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PaymentConsumerJpaEntity that = (PaymentConsumerJpaEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    public PaymentConsumerID getId() {
        return this.id;
    }

    public void setId(final PaymentConsumerID id) {
        this.id = id;
    }

    public PaymentJpaEntity getPayment() {
        return this.payment;
    }

    public void setPayment(final PaymentJpaEntity payment) {
        this.payment = payment;
    }
}
