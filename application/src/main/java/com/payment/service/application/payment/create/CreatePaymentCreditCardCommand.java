package com.payment.service.application.payment.create;

public record CreatePaymentCreditCardCommand(
        String number,
        String name,
        int expirationMonth,
        int expirationYear,
        int cvv
) {
    public static CreatePaymentCreditCardCommand with(
            final String aNumber,
            final String aName,
            final int anExpirationMonth,
            final int anExpirationYear,
            final int aCvv
    ) {
        return new CreatePaymentCreditCardCommand(aNumber, aName, anExpirationMonth, anExpirationYear, aCvv);
    }
}
