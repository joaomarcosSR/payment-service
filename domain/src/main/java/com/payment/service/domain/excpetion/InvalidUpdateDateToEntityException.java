package com.payment.service.domain.excpetion;

import java.io.Serial;
import java.time.Instant;

public class InvalidUpdateDateToEntityException extends NoStacktraceException {
    @Serial
    private static final long serialVersionUID = 1647267903309895636L;

    public InvalidUpdateDateToEntityException(final String entity) {
        super("Trying to set 'updatedAt' with null value on entity :" + entity);
    }

    public InvalidUpdateDateToEntityException(final String entity, final Instant currentDate, final Instant newDate) {
        super(getMessage(entity, currentDate, newDate));
    }

    private static String getMessage(final String entity, final Instant currentDate, final Instant newDate) {
        return "Trying to set expired date to 'updatedAt' on entity: " + entity + ". Current value: "
                + currentDate + ", new value: " + newDate;
    }
}
