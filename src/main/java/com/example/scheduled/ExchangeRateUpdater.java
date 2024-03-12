package com.example.scheduled;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.externalapi.ExchangeRatesApiClient;
import com.example.service.impl.CurrencyServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExchangeRateUpdater {

    @Value("${exchange.rate.updater.fixed-rate}")
    private long fixedRate;

    private final ExchangeRatesApiClient exchangeRatesApiClient;

    private final CurrencyServiceImpl currencyService;

    @Scheduled(fixedRateString = "${exchange.rate.updater.fixed-rate}") // Run every hour
    public void updateExchangeRates() {
        try {
            Map<String, Double> exchangeRates = exchangeRatesApiClient.getExchangeRates();
            exchangeRates.forEach(currencyService::updateCurrencyDetails);
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch exchange rates: " + e.getMessage());
        }
    }
}
