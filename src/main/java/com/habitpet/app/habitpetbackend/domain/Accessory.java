package com.habitpet.app.habitpetbackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.habitpet.app.habitpetbackend.application.dto.AccessoryDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "accessories")
public class Accessory {

    @Id
    private String id = UUID.randomUUID().toString();

    private String name;

    private int coins;

    @ManyToMany(mappedBy = "accessories")
    @JsonBackReference
    private List<Pet> pets = new ArrayList<>();

    public Accessory() {}

    public Accessory(AccessoryDTO dto) {
        this.name = dto.getName();
        this.coins = dto.getCoins();
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<Pet> getPet() {
        return pets;
    }

    public void setPet(List<Pet> pet) {
        this.pets = pet;
    }
}
