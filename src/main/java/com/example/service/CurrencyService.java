package com.example.service;

import java.util.List;
import java.util.Map;

import com.example.dtos.CurrencyDto;

public interface CurrencyService {
    List<CurrencyDto> getAllCurrencies();

    Double getExchangeRate(String code);

    void addCurrency(String code);
}
