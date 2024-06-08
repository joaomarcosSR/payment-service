package com.payment.service.domain.payment;

import com.payment.service.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class PaymentID extends Identifier {
    private final String value;

    private PaymentID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static PaymentID unique() {
        return from(UUID.randomUUID());
    }

    public static PaymentID from(final UUID anId) {
        return from(anId.toString().toLowerCase());
    }

    public static PaymentID from(final String anId) {
        return new PaymentID(anId);
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PaymentID paymentID = (PaymentID) o;
        return Objects.equals(this.value, paymentID.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.value);
    }
}
