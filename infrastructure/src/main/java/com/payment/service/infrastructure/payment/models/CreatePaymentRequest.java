package com.payment.service.infrastructure.payment.models;

public record CreatePaymentRequest(
        String paymentKey,
        String accountId,
        long amount,
        String creditCardNumber,
        String creditCardName,
        int creditCardExpirationMonth,
        int creditCardExpirationYear,
        int creditCardCvv
) {
}
