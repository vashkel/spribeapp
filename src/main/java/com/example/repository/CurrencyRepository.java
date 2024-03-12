package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findByCode(String code);
}
