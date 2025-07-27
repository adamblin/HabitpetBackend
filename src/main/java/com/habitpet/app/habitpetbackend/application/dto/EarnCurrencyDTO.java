package com.habitpet.app.habitpetbackend.application.dto;


import com.habitpet.app.habitpetbackend.domain.enums.CurrencyType;

public record EarnCurrencyDTO(CurrencyType currency, int amount) {}
