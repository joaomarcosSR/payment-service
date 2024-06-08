package com.payment.service.application.payment.create;

import com.payment.service.domain.payment.Payment;
import com.payment.service.domain.payment.PaymentID;
import com.payment.service.domain.payment.PaymentStatus;

public record CreatePaymentOutput(
        String paymentKey,
        PaymentID paymentId,
        PaymentStatus status
) {
    public static CreatePaymentOutput from(final Payment aPayment) {
        return new CreatePaymentOutput(
                aPayment.getPaymentKey(),
                aPayment.getId(),
                aPayment.getStatus()
        );
    }
}
