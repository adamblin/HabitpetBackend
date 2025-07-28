package com.habitpet.app.habitpetbackend.application.dto;

import com.habitpet.app.habitpetbackend.domain.InteractionType;

public class InteractionTypeDTO {
    private String action;
    private String type;

    public InteractionTypeDTO() {}
    public InteractionTypeDTO(String action, String type) {
        this.action = action;
        this.type = type;
    }
    public InteractionTypeDTO(InteractionType interactionType){
        this.action = interactionType.getAction().name();
        this.type = interactionType.getType();
    }


    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
