package com.coralogix.calculator.dao;

import com.coralogix.calculator.model.ExchangeRate;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ExchangeRepository extends ReactiveCrudRepository<ExchangeRate, Long> {
    Mono<ExchangeRate> findByOriginCurrencyAndFinalCurrency(String originCurrency, String finalCurrency);
}
