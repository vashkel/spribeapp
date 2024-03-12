package com.example.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.dtos.CurrencyDto;
import com.example.service.CurrencyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<List<CurrencyDto>> getAllCurrencies() {
        return new ResponseEntity<>(currencyService.getAllCurrencies(), HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Double> getExchangeRate(@PathVariable String code) {
        return new ResponseEntity<>(currencyService.getExchangeRate(code), HttpStatus.OK);
    }

    @PostMapping("/{code}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCurrency(@PathVariable String code) {
        currencyService.addCurrency(code);
    }
}
