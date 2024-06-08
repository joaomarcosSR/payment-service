package com.payment.service.application.payment.update;

import com.payment.service.domain.payment.Payment;
import com.payment.service.domain.payment.PaymentID;
import com.payment.service.domain.payment.PaymentStatus;

public record UpdatePaymentOutput(
        PaymentID id,
        String paymentKey,
        PaymentStatus status,
        String errorMessage
) {
    public static UpdatePaymentOutput from(final Payment aPayment) {
        return new UpdatePaymentOutput(
                aPayment.getId(),
                aPayment.getPaymentKey(),
                aPayment.getStatus(),
                aPayment.getErrorMessage()
        );
    }
}
