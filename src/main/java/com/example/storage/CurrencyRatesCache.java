package com.example.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.dtos.CurrencyDto;

@Component
public class CurrencyRatesCache {

    private final Map<String, Double> exchangeRatesMap = new HashMap<>();
    private final List<CurrencyDto> currencies = new ArrayList<>();

    public List<CurrencyDto> getAllCurrencies() {
        return currencies;
    }

    public Double getExchangeRate(String code) {
        return exchangeRatesMap.get(code);
    }

    public void updateCurrencyDetails(String code, Double rate) {
        exchangeRatesMap.put(code, rate);
        currencies.removeIf(c -> c.getCode().equals(code));
        currencies.add(new CurrencyDto(code, rate));
    }
}
