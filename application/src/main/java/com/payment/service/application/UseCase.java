package com.payment.service.application;

public interface UseCase<IN, OUT> {
    OUT execute(IN aCommand);
}