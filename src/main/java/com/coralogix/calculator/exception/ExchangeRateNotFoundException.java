package com.coralogix.calculator.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExchangeRateNotFoundException extends RuntimeException {
    private String message;
}
