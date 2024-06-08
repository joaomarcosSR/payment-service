package com.payment.service.domain.consumer;

import com.payment.service.domain.ValueObject;

import java.util.Objects;

public class ConsumerEndpointConfig extends ValueObject {
    //TODO: podem ser Object value que vaida se respeita o formato de endpoint
    private final String url;
    private final String token;

    private ConsumerEndpointConfig(
            final String url,
            final String token
    ) {
        this.url = Objects.requireNonNull(url, "'url' is required");
        this.token = Objects.requireNonNull(token, "'token' is required");
    }

    public static ConsumerEndpointConfig newConfig(
            final String anUrl,
            final String anToken
    ) {
        return new ConsumerEndpointConfig(anUrl, anToken);
    }

    public String getUrl() {
        return this.url;
    }

    public String getToken() {
        return this.token;
    }
}
