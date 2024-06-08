package com.payment.service.infrastructure.configuration;

import com.payment.service.infrastructure.configuration.anotations.ProcessTransactionQueue;
import com.payment.service.infrastructure.properties.amqp.QueueProperties;
import com.payment.service.infrastructure.services.EventService;
import com.payment.service.infrastructure.services.impl.RabbitEventService;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {

    @Bean
    @ProcessTransactionQueue
    EventService processTransactionEventService(
            @ProcessTransactionQueue final QueueProperties props,
            final RabbitOperations ops
    ) {
        return new RabbitEventService(props.getExchange(), props.getRoutingKey(), ops);
    }
}
