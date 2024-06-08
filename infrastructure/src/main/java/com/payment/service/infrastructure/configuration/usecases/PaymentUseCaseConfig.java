package com.payment.service.infrastructure.configuration.usecases;

import com.payment.service.application.payment.create.CreatePaymentUseCase;
import com.payment.service.application.payment.create.DefaultCreatePaymentUseCase;
import com.payment.service.application.payment.update.DefaultUpdatePaymentUseCase;
import com.payment.service.application.payment.update.UpdatePaymentUseCase;
import com.payment.service.domain.payment.PaymentGateway;
import com.payment.service.infrastructure.configuration.anotations.ProcessTransactionQueue;
import com.payment.service.infrastructure.services.EventService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentUseCaseConfig {
    private final PaymentGateway paymentGateway;
    private final EventService processTransactionEventService;

    public PaymentUseCaseConfig(final PaymentGateway paymentGateway, @ProcessTransactionQueue final EventService processTransactionEventService) {
        this.paymentGateway = paymentGateway;
        this.processTransactionEventService = processTransactionEventService;
    }
    
    @Bean
    public CreatePaymentUseCase createPaymentUseCase() {
        return new DefaultCreatePaymentUseCase(this.paymentGateway, this.processTransactionEventService::send);
    }

    @Bean
    public UpdatePaymentUseCase updatePaymentUseCase() {
        return new DefaultUpdatePaymentUseCase(this.paymentGateway);
    }
}
