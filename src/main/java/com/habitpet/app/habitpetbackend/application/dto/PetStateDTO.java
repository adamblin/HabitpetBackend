package com.habitpet.app.habitpetbackend.application.dto;

import com.habitpet.app.habitpetbackend.domain.Pet;

public class PetStateDTO {
    private int satiated;
    private int cleanliness;
    private int hapyness;

    public PetStateDTO() {}

    public PetStateDTO(Pet pet) {
        this.satiated = pet.getSatiated();
        this.cleanliness = pet.getCleanliness();
        this.hapyness = pet.getHapyness();
    }
    public PetStateDTO(int satiated, int cleanliness, int hapyness) {
        this.satiated = satiated;
        this.cleanliness = cleanliness;
        this.hapyness = hapyness;
    }

    public int getSatiated() { return satiated; }
    public void setSatiated(int satiated) { this.satiated = satiated; }

    public int getCleanliness() { return cleanliness; }
    public void setCleanliness(int cleanliness) { this.cleanliness = cleanliness; }

    public int getHapyness() { return hapyness; }
    public void setHapyness(int hapyness) { this.hapyness = hapyness; }
}