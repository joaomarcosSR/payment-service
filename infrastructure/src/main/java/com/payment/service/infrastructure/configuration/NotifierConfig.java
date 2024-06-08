package com.payment.service.infrastructure.configuration;

import com.payment.service.infrastructure.consumer.persistence.ConsumerRepository;
import com.payment.service.infrastructure.notifier.ConsumerNotifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class NotifierConfig {
    private final ConsumerRepository consumerRepository;
    private final RestTemplate restTemplate;

    public NotifierConfig(final ConsumerRepository consumerRepository, final RestTemplate restTemplate) {
        this.consumerRepository = consumerRepository;
        this.restTemplate = restTemplate;
    }

    @Bean
    ConsumerNotifier consumerNotifier() {
        return new ConsumerNotifier(this.consumerRepository, this.restTemplate);
    }
}
