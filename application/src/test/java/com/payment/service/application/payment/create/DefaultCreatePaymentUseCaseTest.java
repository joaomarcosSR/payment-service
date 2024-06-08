package com.payment.service.application.payment.create;

import com.payment.service.domain.events.EventPublisher;
import com.payment.service.domain.payment.Payment;
import com.payment.service.domain.payment.PaymentGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

import static com.payment.service.domain.payment.PaymentStatus.PENDING;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
class DefaultCreatePaymentUseCaseTest {

    @InjectMocks
    private DefaultCreatePaymentUseCase useCase;

    @Mock
    private PaymentGateway paymentGateway;
    @Mock
    private EventPublisher eventPublisher;

    @Captor
    private ArgumentCaptor<Payment> paymentCaptor;
    @Captor
    private ArgumentCaptor<CreatePaymentEvent> createPaymentEventCaptor;

    @Test
    void givenAValidCommand_whenCallsCreateCategory_returnCategoryId() {
        final String paymentKey = UUID.randomUUID().toString();
        final String accountId = "125";
        final long amount = 3500;
        final String creditCardNumber = "4000000000000";
        final String creditCardName = "Joseph";
        final LocalDate currentDate = Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
        final int creditCardExpirationMonth = currentDate.getMonthValue();
        final int creditCardExpirationYear = currentDate.getYear() + 1;
        final int creditCardCvv = 987;

        final var aCommand = CreatePaymentCommand.with(
                paymentKey,
                accountId,
                amount,
                CreatePaymentCreditCardCommand.with(
                        creditCardNumber,
                        creditCardName,
                        creditCardExpirationMonth,
                        creditCardExpirationYear,
                        creditCardCvv
                )
        );

        Mockito.when(this.paymentGateway.create(this.paymentCaptor.capture())).thenAnswer(returnsFirstArg());

        final CreatePaymentOutput output = this.useCase.execute(aCommand);

        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.paymentId());
        Assertions.assertEquals(paymentKey, output.paymentKey());
        Assertions.assertEquals(PENDING, output.status());

        final Payment payment = this.paymentCaptor.getValue();
        Assertions.assertNotNull(payment);
        Assertions.assertNotNull(payment.getId());
        Assertions.assertEquals(paymentKey, payment.getPaymentKey());
        Assertions.assertEquals(PENDING, payment.getStatus());
        Assertions.assertNotNull(payment.getCreatedAt());


        Mockito.verify(this.eventPublisher).publishEvent(this.createPaymentEventCaptor.capture());
        final CreatePaymentEvent createPaymentEvent = this.createPaymentEventCaptor.getValue();
        Assertions.assertNotNull(createPaymentEvent);
        Assertions.assertEquals(payment.getCreatedAt(), createPaymentEvent.occurredOn());
        Assertions.assertEquals(accountId, createPaymentEvent.accountId());
        Assertions.assertEquals(amount, createPaymentEvent.amount());
        Assertions.assertEquals(creditCardNumber, createPaymentEvent.creditCardNumber());
        Assertions.assertEquals(creditCardName, createPaymentEvent.creditCardName());
        Assertions.assertEquals(creditCardExpirationMonth, createPaymentEvent.creditCardExpirationMonth());
        Assertions.assertEquals(creditCardExpirationYear, createPaymentEvent.creditCardExpirationYear());
        Assertions.assertEquals(creditCardCvv, createPaymentEvent.creditCardCvv());
    }
}