package com.coralogix.calculator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExchangeRateDTO {
    @JsonProperty("result")
    private String result;
    @JsonProperty("documentation")
    private String documentation;
    @JsonProperty("terms_of_use")
    private String termsOfUse;
    @JsonProperty("time_last_update_unix")
    private Integer timeLastUpdateUnix;
    @JsonProperty("time_last_update_utc")
    private String timeLastUpdateUtc;
    @JsonProperty("time_next_update_unix")
    private Integer timeNextUpdateUnix;
    @JsonProperty("time_next_update_utc")
    private String timeNextUpdateUtc;
    @JsonProperty("base_code")
    private String baseCode;
    @JsonProperty("target_code")
    private String targetCode;
    @JsonProperty("conversion_rate")
    private Double conversionRate;
}
