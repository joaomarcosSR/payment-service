package com.payment.service.domain.payment;

import java.time.Instant;

public record PaymentSearchQuery(
        int page,
        int perPage,
        String sort,
        String direction,
        Instant from,
        Instant to
) {
}
