package com.payment.service.infrastructure.amqp;

import com.payment.service.application.payment.update.UpdatePaymentCommand;
import com.payment.service.application.payment.update.UpdatePaymentUseCase;
import com.payment.service.infrastructure.configuration.json.Json;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PaymentProcessedListener {
    private static final String LISTENER_ID = "processedPaymentListener";

    private final UpdatePaymentUseCase updatePaymentUseCase;

    public PaymentProcessedListener(final UpdatePaymentUseCase updatePaymentUseCase) {
        this.updatePaymentUseCase = updatePaymentUseCase;
    }

    @RabbitListener(id = LISTENER_ID, queues = "${amqp.queues.processed-transaction.queue}")
    public void aPaymentProcessed(@Payload final String value) {
        this.updatePaymentUseCase.execute(Json.readValue(value, UpdatePaymentCommand.class));
    }

}
