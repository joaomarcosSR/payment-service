package com.payment.service.domain.payment;

import com.payment.service.domain.events.Event;

import java.io.Serial;
import java.time.Instant;

public record UpdatedPaymentEvent(
        Instant occurredOn,
        String paymentId,
        String status,
        String errorMessage
) implements Event {
    @Serial
    private static final long serialVersionUID = -8508895045548261973L;

    public static UpdatedPaymentEvent from(final Payment payment) {
        return new UpdatedPaymentEvent(
                payment.getUpdatedAt(),
                payment.getId().getValue(),
                payment.getStatus().name(),
                payment.getErrorMessage()
        );
    }
}
