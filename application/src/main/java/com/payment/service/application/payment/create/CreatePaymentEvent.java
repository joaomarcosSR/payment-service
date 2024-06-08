package com.payment.service.application.payment.create;

import com.payment.service.domain.events.Event;
import com.payment.service.domain.payment.Payment;

import java.time.Instant;

public record CreatePaymentEvent(
        Instant occurredOn,
        String accountId,
        long amount,
        String creditCardNumber,
        String creditCardName,
        int creditCardExpirationMonth,
        int creditCardExpirationYear,
        int creditCardCvv
) implements Event {

    public static CreatePaymentEvent from(final Payment payment, final CreatePaymentCommand aCreateCommand) {
        final CreatePaymentCreditCardCommand creditCard = aCreateCommand.creditCard();
        return new CreatePaymentEvent(
                payment.getCreatedAt(),
                aCreateCommand.accountId(),
                aCreateCommand.amount(),
                creditCard.number(),
                creditCard.name(),
                creditCard.expirationMonth(),
                creditCard.expirationYear(),
                creditCard.cvv()
        );
    }
}
