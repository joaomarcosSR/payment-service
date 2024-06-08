package com.payment.service.infrastructure.payment.models;

import com.payment.service.application.payment.create.CreatePaymentOutput;

public record CreatePaymentResponse(
        String paymentKey,
        String paymentId,
        String status
) {
    public static CreatePaymentResponse from(final CreatePaymentOutput anOutput) {
        return new CreatePaymentResponse(
                anOutput.paymentKey(),
                anOutput.paymentId().getValue(),
                anOutput.status().name()
        );
    }
}
