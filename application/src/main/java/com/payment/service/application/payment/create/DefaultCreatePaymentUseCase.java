package com.payment.service.application.payment.create;

import com.payment.service.application.context.ConsumerContext;
import com.payment.service.domain.events.EventPublisher;
import com.payment.service.domain.payment.Payment;
import com.payment.service.domain.payment.PaymentGateway;

import java.util.Objects;

public class DefaultCreatePaymentUseCase implements CreatePaymentUseCase {
    private final PaymentGateway paymentGateway;
    private final EventPublisher eventPublisher;

    public DefaultCreatePaymentUseCase(final PaymentGateway paymentGateway, final EventPublisher eventPublisher) {
        this.paymentGateway = Objects.requireNonNull(paymentGateway);
        this.eventPublisher = Objects.requireNonNull(eventPublisher);
    }

    @Override
    public CreatePaymentOutput execute(final CreatePaymentCommand aCommand) {
        // Essa logica do publish pode passar para dentro do gateway
        final Payment payment = Payment.newPayment(ConsumerContext.getConsumer().getId(), aCommand.paymentKey());
        this.eventPublisher.publishEvent(CreatePaymentEvent.from(payment, aCommand));
        return CreatePaymentOutput.from(this.paymentGateway.create(payment));
    }
}
