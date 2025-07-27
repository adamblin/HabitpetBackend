package com.habitpet.app.habitpetbackend.application.dto;

import com.habitpet.app.habitpetbackend.domain.User;

public class UserResponseDTO {

    private String id;
    private String username;
    private String email;
    private int habitCoins;
    private int habitGems;

    public UserResponseDTO() {}

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.habitCoins = user.getHabitCoins();
        this.habitGems = user.getHabitGems();
    }

    // Getters y Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHabitCoins() {
        return habitCoins;
    }

    public void setHabitCoins(int habitCoins) {
        this.habitCoins = habitCoins;
    }

    public int getHabitGems() {
        return habitGems;
    }

    public void setHabitGems(int habitGems) {
        this.habitGems = habitGems;
    }
}
