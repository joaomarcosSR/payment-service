package com.payment.service.infrastructure;

import com.payment.service.infrastructure.configuration.WebServerConfig;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("development")
@SpringBootTest(classes = WebServerConfig.class)
@Tag("integrationTest")
public @interface IntegrationTest {
}
