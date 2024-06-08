package com.payment.service.infrastructure.consumer.persistence;

import com.payment.service.domain.consumer.Consumer;
import com.payment.service.domain.consumer.ConsumerEndpointConfig;
import com.payment.service.domain.consumer.ConsumerID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity(name = "consumers")
@Table(name = "Consumers")
public class ConsumerJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "token", nullable = false)
    private String token;
    @Column(name = "update_payment_status_endpoint", nullable = false)
    private String updatePaymentStatusEndpoint;
    @Column(name = "update_payment_status_token")
    private String updatePaymentStatusToken;
    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updatedAt;

    public Consumer toAggregation() {
        return Consumer.with(
                ConsumerID.from(getId()),
                getName(),
                getToken(),
                ConsumerEndpointConfig.newConfig(getUpdatePaymentStatusEndpoint(), getUpdatePaymentStatusToken()),
                getCreatedAt(),
                getUpdatedAt()
        );
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public String getUpdatePaymentStatusEndpoint() {
        return this.updatePaymentStatusEndpoint;
    }

    public void setUpdatePaymentStatusEndpoint(final String updatePaymentStatusEndpoint) {
        this.updatePaymentStatusEndpoint = updatePaymentStatusEndpoint;
    }

    public String getUpdatePaymentStatusToken() {
        return this.updatePaymentStatusToken;
    }

    public void setUpdatePaymentStatusToken(final String updatePaymentStatusToken) {
        this.updatePaymentStatusToken = updatePaymentStatusToken;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(final Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(final Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
