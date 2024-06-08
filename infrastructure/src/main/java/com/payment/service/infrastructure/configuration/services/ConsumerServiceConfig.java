package com.payment.service.infrastructure.configuration.services;

import com.payment.service.application.consumer.service.ConsumerService;
import com.payment.service.application.consumer.service.DefaultConsumerService;
import com.payment.service.domain.consumer.ConsumerGateway;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerServiceConfig {

    private final ConsumerGateway consumerGateway;

    public ConsumerServiceConfig(final ConsumerGateway consumerGateway) {
        this.consumerGateway = consumerGateway;
    }

    @Bean
    public ConsumerService consumerService(final HttpServletRequest request) {
        return new DefaultConsumerService(this.consumerGateway);
    }
}
