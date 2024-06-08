package com.payment.service.application;

public interface UnitUseCase<IN> {
    void execute(IN aCommand);
}