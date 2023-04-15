package com.coralogix.calculator.services;

import com.coralogix.calculator.dao.ExchangeRepository;
import com.coralogix.calculator.dto.ExchangeRateDTO;
import com.coralogix.calculator.model.ExchangeRate;
import com.coralogix.calculator.provider.ExchangeProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ExchangeService {
    private final ExchangeProvider exchangeProvider;

    private final ExchangeRepository exchangeRepository;

    public ExchangeService(ExchangeProvider exchangeProvider, ExchangeRepository exchangeRepository) {
        this.exchangeProvider = exchangeProvider;
        this.exchangeRepository = exchangeRepository;
    }

    public Mono<String> rate(String base, String target) {
        return exchangeRepository.findByOriginCurrencyAndFinalCurrency(base, target)
            .map(ExchangeRate::getValueRate)
            .switchIfEmpty(exchangeProvider.exchangeRate(base, target)
                .flatMap(exchangeRateDTO -> save(exchangeRateDTO))
                .map(ExchangeRate::getValueRate)
                .map(String::valueOf));
    }

    private Mono<ExchangeRate   > save(ExchangeRateDTO exchangeRateDTO) {
        return exchangeRepository.save(new ExchangeRate(null, exchangeRateDTO.getBaseCode(), exchangeRateDTO.getTargetCode(),
                exchangeRateDTO.getTimeNextUpdateUtc(), String.valueOf(exchangeRateDTO.getConversionRate())));
    }

    public Flux<ExchangeRate> getAllExchangeRate() {
        return exchangeRepository.findAll();
    }
}
