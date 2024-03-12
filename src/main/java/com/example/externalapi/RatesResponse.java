package com.example.externalapi;

import java.util.Map;

import lombok.Getter;

@Getter
public class RatesResponse {
    private Map<String, Double> rates;
}
