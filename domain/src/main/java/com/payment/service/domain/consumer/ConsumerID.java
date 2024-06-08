package com.payment.service.domain.consumer;

import com.payment.service.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class ConsumerID extends Identifier {
    private final String value;

    private ConsumerID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static ConsumerID unique() {
        return from(UUID.randomUUID());
    }

    public static ConsumerID from(final UUID anId) {
        return from(anId.toString().toLowerCase());
    }

    public static ConsumerID from(final String anId) {
        return new ConsumerID(anId);
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ConsumerID that = (ConsumerID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
