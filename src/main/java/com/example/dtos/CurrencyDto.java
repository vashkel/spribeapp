package com.example.dtos;

import lombok.Value;

@Value
public class CurrencyDto {

    String code;

    Double exchangeRate;
}
