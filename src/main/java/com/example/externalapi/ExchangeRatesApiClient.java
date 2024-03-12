package com.example.externalapi;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExchangeRatesApiClient {

    private final RestTemplate restTemplate;
    @Value("${external.api.url}")
    private String apiUrl;
    @Value("${external.api.base-currency}")
    private String baseCurrency;

    public Map<String, Double> getExchangeRates() throws IOException {
        String url = String.format("%s?base=%s", apiUrl, baseCurrency);
        RatesResponse response = restTemplate.getForObject(url, RatesResponse.class);
        return response.getRates();
    }

    public Double getExchangeRateByCode(String currencyCode) throws IOException {
        Map<String, Double> allExchangeRates = getExchangeRates();
        return allExchangeRates.get(currencyCode);
    }
}
