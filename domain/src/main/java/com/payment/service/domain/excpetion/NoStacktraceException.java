package com.payment.service.domain.excpetion;

import java.io.Serial;

public class NoStacktraceException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8710601163172291003L;

    public NoStacktraceException(final String message) {
        this(message, null);
    }

    public NoStacktraceException(final String message, final Throwable cause) {
        super(message, cause, true, false);
    }
}
