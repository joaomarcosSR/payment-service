package com.payment.service.infrastructure.payment.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PaymentConsumerID implements Serializable {
    @Serial
    private static final long serialVersionUID = 3172839056521041854L;
    @Column(name = "payment_id", nullable = false)
    private String paymentId;

    @Column(name = "consumer_id", nullable = false)
    private String consumerId;

    public PaymentConsumerID() {
    }

    private PaymentConsumerID(final String aPaymentId, final String aConsumerId) {
        this.paymentId = aPaymentId;
        this.consumerId = aConsumerId;
    }

    public static PaymentConsumerID from(final String aPaymentId, final String aConsumerId) {
        return new PaymentConsumerID(aPaymentId, aConsumerId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PaymentConsumerID that = (PaymentConsumerID) o;
        return Objects.equals(getPaymentId(), that.getPaymentId()) && Objects.equals(getConsumerId(), that.getConsumerId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPaymentId(), getConsumerId());
    }

    public String getPaymentId() {
        return this.paymentId;
    }

    public void setPaymentId(final String paymentId) {
        this.paymentId = paymentId;
    }

    public String getConsumerId() {
        return this.consumerId;
    }

    public void setConsumerId(final String consumerId) {
        this.consumerId = consumerId;
    }
}
