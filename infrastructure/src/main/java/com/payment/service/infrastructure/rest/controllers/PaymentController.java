package com.payment.service.infrastructure.rest.controllers;

import com.payment.service.application.payment.create.CreatePaymentCommand;
import com.payment.service.application.payment.create.CreatePaymentCreditCardCommand;
import com.payment.service.application.payment.create.CreatePaymentOutput;
import com.payment.service.application.payment.create.CreatePaymentUseCase;
import com.payment.service.infrastructure.payment.models.CreatePaymentRequest;
import com.payment.service.infrastructure.rest.PaymentAPI;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@Tag(name = "Payments")
public class PaymentController implements PaymentAPI {
    private final CreatePaymentUseCase createPaymentUseCase;

    public PaymentController(final CreatePaymentUseCase createPaymentUseCase) {
        this.createPaymentUseCase = createPaymentUseCase;
    }

    @Override
    public ResponseEntity<?> createPayment(final CreatePaymentRequest anInput) {
        final CreatePaymentCommand aCommand = CreatePaymentCommand.with(
                anInput.paymentKey(),
                anInput.accountId(),
                anInput.amount(),
                CreatePaymentCreditCardCommand.with(
                        anInput.creditCardNumber(),
                        anInput.creditCardName(),
                        anInput.creditCardExpirationMonth(),
                        anInput.creditCardExpirationYear(),
                        anInput.creditCardCvv()
                )
        );

        final CreatePaymentOutput output = this.createPaymentUseCase.execute(aCommand);
        return ResponseEntity.created(URI.create("/payments/" + output.paymentId())).body(output);
    }
}
