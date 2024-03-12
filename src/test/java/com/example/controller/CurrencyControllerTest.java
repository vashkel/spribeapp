package com.example.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.dtos.CurrencyDto;
import com.example.service.CurrencyService;

@WebMvcTest(CurrencyController.class)
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyService currencyService;

    @Test
    void testGetAllCurrencies() throws Exception {
        List<CurrencyDto> currencies = Arrays.asList(
                new CurrencyDto("USD", 1.0),
                new CurrencyDto("EUR", 0.85),
                new CurrencyDto("GBP", 0.75));

        when(currencyService.getAllCurrencies()).thenReturn(currencies);

        mockMvc.perform(get("/api/currencies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].code", is("USD")))
                .andExpect(jsonPath("$[1].code", is("EUR")))
                .andExpect(jsonPath("$[2].code", is("GBP")));

        verify(currencyService, times(1)).getAllCurrencies();
        verifyNoMoreInteractions(currencyService);
    }

    @Test
    void testGetExchangeRate() throws Exception {
        String code = "USD";

        when(currencyService.getExchangeRate(code)).thenReturn(1.0);

        mockMvc.perform(get("/api/currencies/{code}", code)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(1.0));

        verify(currencyService, times(1)).getExchangeRate(code);
        verifyNoMoreInteractions(currencyService);
    }

    @Test
    void testAddCurrency() throws Exception {
        String code = "USD";
        mockMvc.perform(post("/api/currencies/{code}", code)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(currencyService, times(1)).addCurrency(code);
        verifyNoMoreInteractions(currencyService);
    }
}
