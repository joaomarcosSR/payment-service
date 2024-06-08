package com.payment.service.infrastructure.notifier;

import com.payment.service.domain.events.Event;
import com.payment.service.domain.payment.UpdatedPaymentEvent;
import com.payment.service.infrastructure.consumer.persistence.ConsumerJpaEntity;
import com.payment.service.infrastructure.consumer.persistence.ConsumerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ConsumerNotifier {

    private static final Logger log = LoggerFactory.getLogger(ConsumerNotifier.class);

    private final ConsumerRepository consumerRepository;
    private final RestTemplate restTemplate;

    public ConsumerNotifier(final ConsumerRepository consumerRepository, final RestTemplate restTemplate) {
        this.consumerRepository = consumerRepository;
        this.restTemplate = restTemplate;
    }

    public void notify(final Event event, final String consumerId) {
        if (!(event instanceof UpdatedPaymentEvent)) return;
        final UpdatedPaymentEvent updatedPaymentEvent = (UpdatedPaymentEvent) event;
        final ConsumerJpaEntity consumer = this.consumerRepository.findById(consumerId)
                .orElseThrow(() -> new RuntimeException("Consumer not found"));
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("X-API-TOKEN", consumer.getUpdatePaymentStatusToken());

        final HttpEntity<UpdatedPaymentEvent> request = new HttpEntity<>(updatedPaymentEvent, headers);

        final ResponseEntity<Void> response = this.restTemplate.exchange(consumer.getUpdatePaymentStatusEndpoint(), HttpMethod.POST, request, Void.class);
        log.info("Response status:  {}.", response.getStatusCode());
    }
}
