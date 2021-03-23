package com.mhoveidafar.stock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// To make test works without declaring id in model class (here: Stock)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock {

    // JsonProperty annotation to give different column name in the table in InfoRepository
    // from these declared variables in model class
    @JsonProperty("name")
    private String companyName;
    private long marketCapitalization;
    @JsonProperty("employee")
    private int employeesNumber;
    private double priceEarningRatio;
    private double dividendYieldPercentage;
}
