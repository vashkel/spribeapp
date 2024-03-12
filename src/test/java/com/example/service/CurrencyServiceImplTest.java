package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.externalapi.ExchangeRatesApiClient;
import com.example.model.Currency;
import com.example.repository.CurrencyRepository;
import com.example.service.impl.CurrencyServiceImpl;
import com.example.storage.CurrencyRatesCache;

class CurrencyServiceImplTest {

    @Mock
    CurrencyRatesCache currencyRatesCache;
    @Mock
    private CurrencyRepository currencyRepository;
    @Mock
    private ExchangeRatesApiClient exchangeRatesApiClient;
    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCurrencies() {
        assertNotNull(currencyService.getAllCurrencies());
    }

    @Test
    void getExchangeRate() {
        when(currencyRatesCache.getExchangeRate("USD")).thenReturn(1.0);

        assertEquals(1.0, currencyService.getExchangeRate("USD"));
    }

    @Test
    void addCurrency_Success() throws IOException {
        when(currencyRepository.findByCode("USD")).thenReturn(Optional.empty());
        when(exchangeRatesApiClient.getExchangeRateByCode("USD")).thenReturn(1.0);

        currencyService.addCurrency("USD");

        verify(currencyRepository, times(1)).save(any(Currency.class));
        verify(currencyRatesCache, times(1)).updateCurrencyDetails("USD", 1.0);
    }

    @Test
    void addCurrency_CurrencyExists() {
        when(currencyRepository.findByCode("USD")).thenReturn(Optional.of(new Currency()));

        assertThrows(RuntimeException.class, () -> currencyService.addCurrency("USD"));
    }

    @Test
    void addCurrency_Failure() throws IOException {
        when(exchangeRatesApiClient.getExchangeRateByCode("INVALID")).thenThrow(new IOException("Error"));

        assertThrows(RuntimeException.class, () -> currencyService.addCurrency("INVALID"));
    }
}
