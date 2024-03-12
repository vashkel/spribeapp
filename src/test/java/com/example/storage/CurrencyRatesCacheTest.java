package com.example.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import com.example.dtos.CurrencyDto;

class CurrencyRatesCacheTest {

    private CurrencyRatesCache currencyRatesCache;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        currencyRatesCache = new CurrencyRatesCache();
    }

    @Test
    void getAllCurrencies_shouldReturnEmptyListInitially() {
        List<CurrencyDto> result = currencyRatesCache.getAllCurrencies();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getExchangeRate_shouldReturnNullForNonExistingCurrency() {
        String nonExistingCurrencyCode = "XYZ";
        Double result = currencyRatesCache.getExchangeRate(nonExistingCurrencyCode);

        assertNull(result);
    }

    @Test
    void updateCurrencyDetails_shouldAddCurrencyToCache() {
        String currencyCode = "USD";
        Double exchangeRate = 1.0;

        currencyRatesCache.updateCurrencyDetails(currencyCode, exchangeRate);

        List<CurrencyDto> currencies = currencyRatesCache.getAllCurrencies();
        assertEquals(1, currencies.size());

        CurrencyDto updatedCurrency = currencies.get(0);
        assertEquals(currencyCode, updatedCurrency.getCode());
        assertEquals(exchangeRate, updatedCurrency.getExchangeRate());

        Double updatedExchangeRate = currencyRatesCache.getExchangeRate(currencyCode);
        assertEquals(exchangeRate, updatedExchangeRate);
    }

    @Test
    void updateCurrencyDetails_shouldUpdateExistingCurrencyInCache() {
        String currencyCode = "USD";
        Double initialExchangeRate = 1.0;
        Double updatedExchangeRate = 1.5;

        currencyRatesCache.updateCurrencyDetails(currencyCode, initialExchangeRate);
        currencyRatesCache.updateCurrencyDetails(currencyCode, updatedExchangeRate);

        List<CurrencyDto> currencies = currencyRatesCache.getAllCurrencies();
        assertEquals(1, currencies.size());

        CurrencyDto updatedCurrency = currencies.get(0);
        assertEquals(currencyCode, updatedCurrency.getCode());
        assertEquals(updatedExchangeRate, updatedCurrency.getExchangeRate());

        Double finalExchangeRate = currencyRatesCache.getExchangeRate(currencyCode);
        assertEquals(updatedExchangeRate, finalExchangeRate);
    }
}
