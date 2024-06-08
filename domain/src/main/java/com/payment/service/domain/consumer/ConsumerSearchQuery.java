package com.payment.service.domain.consumer;

public record ConsumerSearchQuery(
        int page,
        int perPage,
        String sort,
        String direction
) {
}
