package com.habitpet.app.habitpetbackend.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.habitpet.app.habitpetbackend.domain.Task;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TaskInDTO(
        String name,
        int estimatedTime,
        String type,
        String status) {

}
