package com.habitpet.app.habitpetbackend.application.dto;

import com.habitpet.app.habitpetbackend.domain.Accessory;
import com.habitpet.app.habitpetbackend.domain.enums.CurrencyType;

public record AccessoryDTO(String id, String name, int habitCoins, int habitGems) {

    public static AccessoryDTO fromEntity(Accessory accessory) {
        return new AccessoryDTO(
                accessory.getId(),
                accessory.getName(),
                accessory.getHabitCoinsCost(),
                accessory.getHabitGemsCost()
        );
    }
}
