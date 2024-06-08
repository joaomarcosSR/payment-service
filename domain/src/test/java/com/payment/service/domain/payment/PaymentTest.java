package com.payment.service.domain.payment;

import com.payment.service.domain.consumer.ConsumerID;
import com.payment.service.domain.events.Event;
import com.payment.service.domain.events.EventPublisher;
import com.payment.service.domain.excpetion.InvalidPaymentErrorMessageException;
import com.payment.service.domain.excpetion.InvalidPaymentStatusToUpdateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class PaymentTest {
    private final ConsumerID consumerId = ConsumerID.unique();
    private final String paymentKey = "paymentKey";

    @Test
    void givenValidParams_whenCallsNewPayment_shouldCreateCorrectly() {
        final Payment actualPayment = Payment.newPayment(this.consumerId, this.paymentKey);

        Assertions.assertNotNull(actualPayment);
        Assertions.assertNotNull(actualPayment.getId());
        Assertions.assertEquals(this.consumerId, actualPayment.getConsumerId());
        Assertions.assertEquals(this.paymentKey, actualPayment.getPaymentKey());
        Assertions.assertEquals(PaymentStatus.PENDING, actualPayment.getStatus());
        Assertions.assertNull(actualPayment.getErrorMessage());
        Assertions.assertNotNull(actualPayment.getCreatedAt());
        Assertions.assertNotNull(actualPayment.getUpdatedAt());
    }

    @Test
    void givenAPendingPayment_whenCallsUpdate_shouldUpdateCorrectly() {
        final Payment payment = Payment.newPayment(this.consumerId, this.paymentKey);
        Assertions.assertEquals(PaymentStatus.PENDING, payment.getStatus());
        final Instant oldUpdatedAt = payment.getUpdatedAt();
        assertEmptyEvents(payment);

        payment.approve();

        Assertions.assertEquals(PaymentStatus.APPROVED, payment.getStatus());
        Assertions.assertTrue(payment.getUpdatedAt().isAfter(oldUpdatedAt));
        assertUpdatedEventFilled(payment);
    }

    @Test
    void givenApprovedPayment_whenCallsUpdate_doNothing() {
        final Payment payment = Payment.newPayment(this.consumerId, this.paymentKey);
        payment.approve();
        clearEvents(payment);

        Assertions.assertEquals(PaymentStatus.APPROVED, payment.getStatus());
        final Instant oldUpdatedAt = payment.getUpdatedAt();

        payment.approve();

        Assertions.assertEquals(PaymentStatus.APPROVED, payment.getStatus());
        Assertions.assertEquals(oldUpdatedAt, payment.getUpdatedAt());
        assertUpdatedEventFilled(payment);
    }

    @Test
    void givenAPendingPayment_whenCallsReject_shouldUpdateCorrectly() {
        final String errorMessage = "User has no limit";
        final Payment payment = Payment.newPayment(this.consumerId, this.paymentKey);

        Assertions.assertEquals(PaymentStatus.PENDING, payment.getStatus());
        Assertions.assertNull(payment.getErrorMessage());
        final Instant oldUpdatedAt = payment.getUpdatedAt();
        assertEmptyEvents(payment);

        payment.reject(errorMessage);

        Assertions.assertEquals(PaymentStatus.REJECTED, payment.getStatus());
        Assertions.assertEquals(errorMessage, payment.getErrorMessage());
        Assertions.assertTrue(payment.getUpdatedAt().isAfter(oldUpdatedAt));
        assertUpdatedEventFilled(payment);
    }

    @Test
    void givenAPendingPaymentAndInvalidErrorMessage_whenCallsReject_throwError() {
        final Payment payment = Payment.newPayment(this.consumerId, this.paymentKey);
        assertEmptyEvents(payment);

        Assertions.assertEquals(PaymentStatus.PENDING, payment.getStatus());

        Assertions.assertThrows(InvalidPaymentErrorMessageException.class, () -> payment.reject(""));
        Assertions.assertEquals(PaymentStatus.PENDING, payment.getStatus());
        assertEmptyEvents(payment);
    }

    @Test
    void givenRejectedPayment_whenCallsReject_doNothing() {
        final String errorMessage = "User has no limit";
        final Payment payment = Payment.newPayment(this.consumerId, this.paymentKey);
        payment.reject("old error");

        Assertions.assertEquals(PaymentStatus.REJECTED, payment.getStatus());
        final String oldErrorMessage = payment.getErrorMessage();
        final Instant oldUpdatedAt = payment.getUpdatedAt();
        clearEvents(payment);

        payment.reject(errorMessage);

        Assertions.assertEquals(PaymentStatus.REJECTED, payment.getStatus());
        Assertions.assertEquals(oldErrorMessage, payment.getErrorMessage());
        Assertions.assertEquals(oldUpdatedAt, payment.getUpdatedAt());
        assertUpdatedEventFilled(payment);
    }

    @Test
    void givenNoPendingPayment_whenCallsUpdate_throwsError() {
        final Payment payment = Payment.newPayment(this.consumerId, this.paymentKey);
        payment.reject("error");
        Assertions.assertEquals(PaymentStatus.REJECTED, payment.getStatus());
        clearEvents(payment);

        final var exception = Assertions.assertThrows(InvalidPaymentStatusToUpdateException.class, payment::approve);
        Assertions.assertEquals(PaymentStatus.REJECTED, payment.getStatus());
        Assertions.assertEquals("Could not update payment to status APPROVED. The current status REJECTED is invalid to this operation.", exception.getMessage());
        assertEmptyEvents(payment);
    }

    @Test
    void givenNoPendingPayment_whenCallsReject_throwsError() {
        final Payment payment = Payment.newPayment(this.consumerId, this.paymentKey);
        payment.approve();
        Assertions.assertEquals(PaymentStatus.APPROVED, payment.getStatus());
        clearEvents(payment);

        final var exception = Assertions.assertThrows(InvalidPaymentStatusToUpdateException.class, () -> payment.reject("error"));
        Assertions.assertEquals(PaymentStatus.APPROVED, payment.getStatus());
        Assertions.assertEquals("Could not update payment to status REJECTED. The current status APPROVED is invalid to this operation.", exception.getMessage());
        assertEmptyEvents(payment);
    }

    private void clearEvents(final Payment payment) {
        payment.publishDomainEvents(event -> {
        });
    }

    private void assertEmptyEvents(final Payment payment) {
        final List<Event> events = getDomainEvents(payment);
        Assertions.assertTrue(events.isEmpty());
    }

    private void assertUpdatedEventFilled(final Payment payment) {
        final List<Event> events = getDomainEvents(payment);

        Assertions.assertEquals(1, events.size());
        Assertions.assertInstanceOf(UpdatedPaymentEvent.class, events.getFirst());
        final UpdatedPaymentEvent event = (UpdatedPaymentEvent) events.getFirst();
        Assertions.assertEquals(payment.getUpdatedAt(), event.occurredOn());
        Assertions.assertEquals(payment.getStatus(), event.status());
        Assertions.assertEquals(payment.getErrorMessage(), event.errorMessage());
    }

    private List<Event> getDomainEvents(final Payment payment) {
        final List<Event> events = new ArrayList<>();
        final EventPublisher eventPublisher = new EventPublisherDummy(events::add);
        payment.publishDomainEvents(eventPublisher);
        return events;
    }

    static class EventPublisherDummy implements EventPublisher {
        private final Consumer<Event> eventRegister;

        EventPublisherDummy(final Consumer<Event> eventRegister) {
            this.eventRegister = eventRegister;
        }

        @Override
        public void publishEvent(final Event event) {
            this.eventRegister.accept(event);
        }
    }

}