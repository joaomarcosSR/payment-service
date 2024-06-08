package com.payment.service.domain;

import com.payment.service.domain.events.Event;
import com.payment.service.domain.events.EventPublisher;
import com.payment.service.domain.excpetion.InvalidUpdateDateToEntityException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class Entity<ID extends Identifier> {
    protected final ID id;
    private final Instant createdAt;
    private Instant updatedAt;
    private final List<Event> events;

    protected Entity(final ID anId, final Instant aCreateDate, final Instant anUpdateDate) {
        this.id = Objects.requireNonNull(anId, "'id' should not be null.");
        this.createdAt = Objects.requireNonNull(aCreateDate, "'createdAt' is required");
        this.updatedAt = Objects.requireNonNull(anUpdateDate, "'updatedAt' is required");
        this.events = new ArrayList<>();
        if (this.createdAt.isAfter(this.updatedAt))
            throw new InvalidUpdateDateToEntityException(getClassName(), this.createdAt, this.updatedAt);
    }

    public ID getId() {
        return this.id;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    protected void setUpdatedDate(final Instant anUpdateDate) {
        if (anUpdateDate == null) throw new InvalidUpdateDateToEntityException(getClassName());
        if (anUpdateDate.isBefore(this.updatedAt))
            throw new InvalidUpdateDateToEntityException(getClassName(), this.updatedAt, anUpdateDate);
        this.updatedAt = anUpdateDate;
    }

    //TODO: add a publishDomainEvent that receive a DomainEventPublisherFactory, with receives a event type and return a publish list to this event

    public void publishDomainEvents(final EventPublisher publisher) {
        if (publisher == null) return;
        getDomainEvents().forEach(publisher::publishEvent);
        this.events.clear();
    }

    public void registerEvent(final Event event) {
        if (event == null) return;
        this.events.add(event);
    }

    private List<Event> getDomainEvents() {
        return Collections.unmodifiableList(this.events);
    }

    private String getClassName() {
        return this.getClass().getName();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Entity<?> entity = (Entity<?>) o;
        return Objects.equals(getId(), entity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
