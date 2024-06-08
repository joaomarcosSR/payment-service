package com.payment.service.domain.excpetion;

import java.io.Serial;

public class InvalidPaymentErrorMessageException extends NoStacktraceException {
    @Serial
    private static final long serialVersionUID = -2176673783003451647L;

    public InvalidPaymentErrorMessageException() {
        super("Error Message is required to reject a payment");
    }
}
