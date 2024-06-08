package com.payment.service.domain.consumer;

import com.payment.service.domain.AggregateRoot;

import java.time.Instant;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

public class Consumer extends AggregateRoot<ConsumerID> {
    private final String name;
    private final String token;
    private ConsumerEndpointConfig updatePaymentStatusConfig;

    private Consumer(
            final ConsumerID anId,
            final String aName,
            final String aToken,
            final ConsumerEndpointConfig anUpdatePaymentStatusConfig,
            final Instant aCreateDate,
            final Instant aUpdateDate
    ) {
        super(anId, aCreateDate, aUpdateDate);
        this.name = Objects.requireNonNull(aName, "'name' is required");
        this.token = Objects.requireNonNull(aToken, "'token' is required");
        this.updatePaymentStatusConfig = anUpdatePaymentStatusConfig;
    }

    public static Consumer newConsumer(final String aName) {
        final ConsumerID id = ConsumerID.unique();
        final Instant currentDate = Instant.now();
        final String token = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
        return with(id, aName, token, null, currentDate, currentDate);
    }

    public static Consumer with(
            final ConsumerID anId,
            final String aName,
            final String aToken,
            final ConsumerEndpointConfig anUpdatePaymentStatusConfig,
            final Instant aCreateDate,
            final Instant aUpdateDate
    ) {
        return new Consumer(anId, aName, aToken, anUpdatePaymentStatusConfig, aCreateDate, aUpdateDate);
    }

    public String getName() {
        return this.name;
    }

    public String getToken() {
        return this.token;
    }

    public ConsumerEndpointConfig getUpdatePaymentStatusConfig() {
        return this.updatePaymentStatusConfig;
    }

    public void setUpdatePaymentStatusConfig(final String anUrl, final String anToken) {
        this.updatePaymentStatusConfig = ConsumerEndpointConfig.newConfig(anUrl, anToken);
        setUpdatedDate(Instant.now());
    }
}
