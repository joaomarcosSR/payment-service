package com.payment.service.application.payment.update;

import com.payment.service.domain.payment.Payment;
import com.payment.service.domain.payment.PaymentGateway;
import com.payment.service.domain.payment.PaymentID;

public class DefaultUpdatePaymentUseCase implements UpdatePaymentUseCase {
    private final PaymentGateway paymentGateway;

    public DefaultUpdatePaymentUseCase(final PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    @Override
    public UpdatePaymentOutput execute(final UpdatePaymentCommand aCommand) {
        final Payment payment = this.paymentGateway.findById(PaymentID.from(aCommand.id()))
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if ("APPROVED".equals(aCommand.status())) payment.approve();
        else if ("REJECTED".equals(aCommand.status())) payment.reject(aCommand.errorMessage());
        else throw new RuntimeException("Invalid status to update payment.");

        return UpdatePaymentOutput.from(this.paymentGateway.update(payment));
    }
}
