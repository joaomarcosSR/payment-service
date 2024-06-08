package com.payment.service.application.payment.update;

public record UpdatePaymentCommand(
        String id,
        String status,
        String errorMessage
) {
    public static UpdatePaymentCommand with(
            final String anId,
            final String anStatus,
            final String anErrorMessage
    ) {
        return new UpdatePaymentCommand(anId, anStatus, anErrorMessage);
    }
}
