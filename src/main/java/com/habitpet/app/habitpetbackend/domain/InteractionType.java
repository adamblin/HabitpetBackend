package com.habitpet.app.habitpetbackend.domain;

import com.habitpet.app.habitpetbackend.domain.enums.ActionType;
import jakarta.persistence.*;

import java.util.UUID;
@Entity
@Table(name = "interaction_types")
public class InteractionType {
    @Id
    private String id = UUID.randomUUID().toString();
    @Enumerated(EnumType.STRING)
    private ActionType action;
    private String type;

    private int deltaSatiated;
    private int deltaCleanliness;
    private int deltaHapyness;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDeltaSatiated() {
        return deltaSatiated;
    }

    public void setDeltaSatiated(int deltaSatiated) {
        this.deltaSatiated = deltaSatiated;
    }

    public int getDeltaCleanliness() {
        return deltaCleanliness;
    }

    public void setDeltaCleanliness(int deltaCleanliness) {
        this.deltaCleanliness = deltaCleanliness;
    }

    public int getDeltaHapyness() {
        return deltaHapyness;
    }

    public void setDeltaHapyness(int deltaHapyness) {
        this.deltaHapyness = deltaHapyness;
    }
}
