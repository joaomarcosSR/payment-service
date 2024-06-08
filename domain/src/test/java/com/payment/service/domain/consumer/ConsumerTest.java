package com.payment.service.domain.consumer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

class ConsumerTest {
    String consumerName = "Shopping";

    @Test
    void givenValidParams_whenCallsNewConsumer_shouldCreateCorrectly() {
        final Consumer actualConsumer = Consumer.newConsumer(this.consumerName);

        Assertions.assertNotNull(actualConsumer);
        Assertions.assertNotNull(actualConsumer.getId());
        Assertions.assertEquals(this.consumerName, actualConsumer.getName());
        Assertions.assertNotNull(actualConsumer.getToken());
    }

    @Test
    void givenValidConsume_whenCallsSetUpdatePaymentStatusConfig_shouldUpdateCorrectly() {
        final String endpointUrl = "endpoint";
        final String endpointToken = "token";

        final Consumer consumer = Consumer.newConsumer(this.consumerName);
        Assertions.assertNull(consumer.getUpdatePaymentStatusConfig());
        final Instant oldUpdateDate = consumer.getUpdatedAt();

        consumer.setUpdatePaymentStatusConfig(endpointUrl, endpointToken);

        final ConsumerEndpointConfig updatePaymentStatusConfig = consumer.getUpdatePaymentStatusConfig();
        Assertions.assertNotNull(updatePaymentStatusConfig);
        Assertions.assertEquals(endpointUrl, updatePaymentStatusConfig.getUrl());
        Assertions.assertEquals(endpointToken, updatePaymentStatusConfig.getToken());
        Assertions.assertTrue(consumer.getUpdatedAt().isAfter(oldUpdateDate));
    }
}