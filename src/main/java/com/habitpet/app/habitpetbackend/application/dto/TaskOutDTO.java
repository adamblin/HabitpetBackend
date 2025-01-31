package com.habitpet.app.habitpetbackend.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.habitpet.app.habitpetbackend.domain.Task;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TaskOutDTO(
        String id,
        String name,
        int estimatedTime,
        String type,
        String status,
        int points
) {
    public TaskOutDTO(Task task) {
        this(task.getId(), task.getName(), task.getEstimatedTime(), task.getType(), task.getStatus(), task.getPoints());
    }
}
