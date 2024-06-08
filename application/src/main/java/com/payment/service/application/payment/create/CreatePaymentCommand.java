package com.payment.service.application.payment.create;

import java.util.Objects;

public record CreatePaymentCommand(
        String paymentKey,
        String accountId,
        long amount,
        CreatePaymentCreditCardCommand creditCard
) {
    public static CreatePaymentCommand with(
            final String aPaymentKey,
            final String anAccountId,
            final long anAmount,
            final CreatePaymentCreditCardCommand aCreditCard
    ) {
        return new CreatePaymentCommand(
                aPaymentKey,
                anAccountId,
                anAmount,
                Objects.requireNonNull(aCreditCard)
        );
    }
}
