package com.habitpet.app.habitpetbackend.application.dto;

import com.habitpet.app.habitpetbackend.domain.Accessory;

public class AccessoryDTO {


    private String name;
    private int coins;

    public AccessoryDTO() {}

    public AccessoryDTO(Accessory accessory) {
        this.name = accessory.getName();
        this.coins = accessory.getCoins();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
