package com.payment.service.domain.events;

import java.io.Serializable;
import java.time.Instant;

public interface Event extends Serializable {
    Instant occurredOn();
}
