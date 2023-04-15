package com.coralogix.calculator.provider;

import com.coralogix.calculator.dao.ExchangeRepository;
import com.coralogix.calculator.dto.ExchangeRateDTO;
import com.coralogix.calculator.exception.ExchangeProviderException;
import com.coralogix.calculator.model.ExchangeRate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ExchangeProvider {
    private final String URL = "https://v6.exchangerate-api.com/v6/1227cf5d13731ad6c251bc00/pair/%s/%s";
    private final WebClient webClient;
    private final ExchangeRepository exchangeRepository;


    public ExchangeProvider(WebClient webClient, ExchangeRepository exchangeRepository) {
        this.webClient = webClient;
        this.exchangeRepository = exchangeRepository;
    }

    public Mono<ExchangeRateDTO> exchangeRate(String base, String target) {
        return webClient.get()
            .uri(String.format(URL, base, target))
            .retrieve()
            .bodyToMono(ExchangeRateDTO.class)
            .doOnNext(exchangeRateDTO -> saveExchangeRate(exchangeRateDTO))
            .onErrorResume(error -> errorApi(error, base, target));
    }

    private Mono<? extends ExchangeRateDTO> errorApi(Throwable error, String base, String target) {
        String message = String.format("error en la llama del api externa %s %s %s", base, target, error.getMessage());
        log.error(message);
        return Mono.error(new ExchangeProviderException(message));
    }

    private void saveExchangeRate(ExchangeRateDTO exchangeRateDTO) {
            exchangeRepository.save(new ExchangeRate(null, exchangeRateDTO.getBaseCode(), exchangeRateDTO.getTargetCode(),
                exchangeRateDTO.getTimeNextUpdateUtc(), String.valueOf(exchangeRateDTO.getConversionRate())))
                .toFuture().isCompletedExceptionally();

    }
}
