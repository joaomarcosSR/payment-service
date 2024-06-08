package com.payment.service.domain;

import java.time.Instant;

public abstract class AggregateRoot<ID extends Identifier> extends Entity<ID> {
    protected AggregateRoot(final ID anId, final Instant aCreateDate, final Instant anUpdateDate) {
        super(anId, aCreateDate, anUpdateDate);
    }
}
