package com.payment.service.infrastructure.configuration;

import com.payment.service.infrastructure.configuration.anotations.ProcessTransactionQueue;
import com.payment.service.infrastructure.configuration.anotations.ProcessedTransactionQueue;
import com.payment.service.infrastructure.configuration.anotations.TransactionEvents;
import com.payment.service.infrastructure.properties.amqp.QueueProperties;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmpqConfig {

    @Bean
    @ConfigurationProperties("amqp.queues.process-transaction")
    @ProcessTransactionQueue
    QueueProperties processTransactionQueueProperties() {
        return new QueueProperties();
    }

    @Bean
    @ConfigurationProperties("amqp.queues.processed-transaction")
    @ProcessedTransactionQueue
    QueueProperties processedTransactionQueueProperties() {
        return new QueueProperties();
    }

    @Configuration
    static class Admin {

        @Bean
        @TransactionEvents
        Exchange transactionEventsExchange(@ProcessTransactionQueue final QueueProperties props) {
            return new DirectExchange(props.getExchange());
        }

        @Bean
        @ProcessTransactionQueue
        Queue processTransactionQueue(@ProcessTransactionQueue final QueueProperties props) {
            return new Queue(props.getQueue());
        }

        @Bean
        @ProcessTransactionQueue
        Binding processTransactionQueueBinding(
                @TransactionEvents final DirectExchange exchange,
                @ProcessTransactionQueue final Queue queue,
                @ProcessTransactionQueue final QueueProperties props
        ) {
            return BindingBuilder.bind(queue).to(exchange).with(props.getRoutingKey());
        }

        @Bean
        @ProcessedTransactionQueue
        Queue processedTransactionQueue(@ProcessedTransactionQueue final QueueProperties props) {
            return new Queue(props.getQueue());
        }

        @Bean
        @ProcessedTransactionQueue
        Binding processedTransactionQueueBinding(
                @TransactionEvents final DirectExchange exchange,
                @ProcessedTransactionQueue final Queue queue,
                @ProcessedTransactionQueue final QueueProperties props
        ) {
            return BindingBuilder.bind(queue).to(exchange).with(props.getRoutingKey());
        }
    }
}
