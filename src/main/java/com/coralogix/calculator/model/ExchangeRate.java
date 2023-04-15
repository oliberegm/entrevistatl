package com.coralogix.calculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@AllArgsConstructor
@Getter
@NoArgsConstructor
@Table
public class ExchangeRate {
    @Id
    private Long id;

    private String originCurrency;

    private String finalCurrency;

    private String dateNext;

    private String valueRate;
}
