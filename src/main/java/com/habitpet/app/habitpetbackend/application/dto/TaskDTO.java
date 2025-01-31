package com.habitpet.app.habitpetbackend.application.dto;

import com.habitpet.app.habitpetbackend.domain.Task;

public class TaskDTO {

    private String id;
    private String name;
    private int estimatedTime;
    private String type;
    private String status;
    private int points;

    public TaskDTO() {}

    public TaskDTO(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.estimatedTime = task.getEstimatedTime();
        this.type = task.getType();
        this.status = task.getStatus();
    }

    // Getters and Setters
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

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
