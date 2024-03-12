package com.example.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dtos.CurrencyDto;
import com.example.externalapi.ExchangeRatesApiClient;
import com.example.model.Currency;
import com.example.repository.CurrencyRepository;
import com.example.service.CurrencyService;
import com.example.storage.CurrencyRatesCache;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyRatesCache currencyRatesCache;
    private final ExchangeRatesApiClient exchangeRatesApiClient;

    @Override
    public List<CurrencyDto> getAllCurrencies() {
        return currencyRatesCache.getAllCurrencies();
    }

    @Override
    public Double getExchangeRate(String code) {
        return currencyRatesCache.getExchangeRate(code);
    }

    @Override
    @Transactional
    public void addCurrency(String code) {
        if (currencyRepository.findByCode(code).isPresent()) {
            throw new RuntimeException("Currency already exists");
        }
        try {
            Double rate = exchangeRatesApiClient.getExchangeRateByCode(code);
            createCurrencyDetails(code, rate);
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch exchange rate: " + e.getMessage());
        }
    }

    private void createCurrencyDetails(final String code, final Double rate) {
        Currency currency = new Currency();
        save(currency, code, rate);
    }

    @Transactional
    public void updateCurrencyDetails(String code, Double rate) {
        Optional<Currency> currency = currencyRepository.findByCode(code);
        currency.ifPresent(value -> save(value, code, rate));
    }

    private void save(Currency currency, String code, Double rate) {
        currency.setCode(code);
        currency.setExchangeRate(rate);
        currencyRepository.save(currency);

        currencyRatesCache.updateCurrencyDetails(code, rate);
    }
}