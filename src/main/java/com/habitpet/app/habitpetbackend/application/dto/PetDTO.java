package com.habitpet.app.habitpetbackend.application.dto;

import com.habitpet.app.habitpetbackend.domain.Pet;

public class PetDTO {

    private String name;

    public PetDTO() {}

    public PetDTO(Pet pet) {
        this.name = pet.getName();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
