package com.payment.service.domain.payment;

import com.payment.service.domain.AggregateRoot;
import com.payment.service.domain.consumer.ConsumerID;
import com.payment.service.domain.excpetion.InvalidPaymentErrorMessageException;
import com.payment.service.domain.excpetion.InvalidPaymentStatusToUpdateException;

import java.time.Instant;
import java.util.Objects;

import static com.payment.service.domain.payment.PaymentStatus.*;

public class Payment extends AggregateRoot<PaymentID> {
    private final ConsumerID consumerId;
    private final String paymentKey;
    private PaymentStatus status;
    private String errorMessage;

    private Payment(
            final PaymentID anId,
            final ConsumerID aConsumerId,
            final String aPaymentKey,
            final PaymentStatus anStatus,
            final String anErrorMessage,
            final Instant aCreateDate,
            final Instant aUpdateDate
    ) {
        super(anId, aCreateDate, aUpdateDate);
        this.consumerId = Objects.requireNonNull(aConsumerId, "'consumerId' is required");
        this.paymentKey = Objects.requireNonNull(aPaymentKey, "'paymentKey' is required");
        this.status = Objects.requireNonNull(anStatus, "'status' is required");
        this.errorMessage = anErrorMessage;
    }

    public static Payment newPayment(final ConsumerID aConsumerId, final String aPaymentKey) {
        final PaymentID id = PaymentID.unique();
        final Instant currentDate = Instant.now();
        return with(
                id,
                aConsumerId,
                aPaymentKey,
                PENDING,
                null,
                currentDate,
                currentDate
        );
    }

    public static Payment with(final PaymentID anId,
                               final ConsumerID aConsumerId,
                               final String aPaymentKey,
                               final PaymentStatus anStatus,
                               final String anErrorMessage,
                               final Instant aCreateDate,
                               final Instant aUpdateDate
    ) {
        return new Payment(anId, aConsumerId, aPaymentKey, anStatus, anErrorMessage, aCreateDate, aUpdateDate);
    }

    public void approve() {
        if (this.status == APPROVED) {
            registerEvent(UpdatedPaymentEvent.from(this));
            return;
        }

        if (this.status != PENDING) throw new InvalidPaymentStatusToUpdateException(APPROVED, this.status);
        this.status = APPROVED;
        setUpdatedDate(Instant.now());
        registerEvent(UpdatedPaymentEvent.from(this));
    }

    public void reject(final String anErrorMessage) {
        if (this.status == REJECTED) {
            registerEvent(UpdatedPaymentEvent.from(this));
            return;
        }

        if (anErrorMessage == null || anErrorMessage.isBlank()) throw new InvalidPaymentErrorMessageException();
        if (this.status != PENDING) throw new InvalidPaymentStatusToUpdateException(REJECTED, this.status);
        this.status = REJECTED;
        this.errorMessage = anErrorMessage;
        setUpdatedDate(Instant.now());
        registerEvent(UpdatedPaymentEvent.from(this));
    }

    @Override
    public PaymentID getId() {
        return this.id;
    }

    public ConsumerID getConsumerId() {
        return this.consumerId;
    }

    public String getPaymentKey() {
        return this.paymentKey;
    }

    public PaymentStatus getStatus() {
        return this.status;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}