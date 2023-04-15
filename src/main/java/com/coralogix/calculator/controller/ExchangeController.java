package com.coralogix.calculator.controller;

import com.coralogix.calculator.exception.ExchangeRateNotFoundException;
import com.coralogix.calculator.model.ExchangeRate;
import com.coralogix.calculator.services.ExchangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
@Slf4j
public class ExchangeController {

    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("exchangeRate/{originCurrency}/{finalCurrency}")
    public Mono<String> getExchangeRate(@PathVariable String originCurrency, @PathVariable String finalCurrency) {
        return exchangeService.rate(originCurrency, finalCurrency)
            .onErrorResume(error -> errorNotFound(error, originCurrency, finalCurrency));
    }

    private Mono<? extends String> errorNotFound(Throwable error, String originCurrency, String finalCurrency) {
        String message = String.format("error not found rate %s %s %s", originCurrency, finalCurrency, error.getMessage());
        log.error(message);
        return Mono.error(new ExchangeRateNotFoundException(message));
    }

    @GetMapping("allExchangeRate")
    public Flux<ExchangeRate> getAllExchangeRate() {
        return exchangeService.getAllExchangeRate();
    }
}
