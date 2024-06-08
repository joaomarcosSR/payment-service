package com.payment.service.domain.excpetion;

import com.payment.service.domain.payment.PaymentStatus;

import java.io.Serial;

public class InvalidPaymentStatusToUpdateException extends NoStacktraceException {
    @Serial
    private static final long serialVersionUID = -2176673783003451647L;

    public InvalidPaymentStatusToUpdateException(final PaymentStatus newStatus, final PaymentStatus currentStatus) {
        super(getMessage(newStatus, currentStatus));
    }

    private static String getMessage(final PaymentStatus newStatus, final PaymentStatus currentStatus) {
        return "Could not update payment to status " + newStatus + ". The current status " +
                currentStatus + " is invalid to this operation.";
    }
}
