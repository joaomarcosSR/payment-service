package com.payment.service.domain.consumer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConsumerEndpointConfigTest {
    @Test
    void givenValidParams_whenCallsNewConfig_shouldCreateCorrectly() {
        final String endpointUrl = "endpoint";
        final String endpointToken = "token";

        final ConsumerEndpointConfig actualConfig = ConsumerEndpointConfig.newConfig(endpointUrl, endpointToken);

        Assertions.assertNotNull(actualConfig);
        Assertions.assertEquals(endpointUrl, actualConfig.getUrl());
        Assertions.assertEquals(endpointToken, actualConfig.getToken());
    }
}