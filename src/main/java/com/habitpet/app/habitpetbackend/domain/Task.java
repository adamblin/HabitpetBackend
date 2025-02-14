package com.habitpet.app.habitpetbackend.domain;

import com.habitpet.app.habitpetbackend.application.dto.TaskDTO;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="tasks")
public class Task {

    @Id
    private String id = UUID.randomUUID().toString();

    private String name;
    private int estimatedTime;
    private String type; // TO-DO HACER QUE SEA ESTATICO Y QUE SOLO PUEDA CONTENER TO DO, DOING , DONDE O ALGO ASI
    private String status;
    @Column(nullable = false)
    private int points = 0;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Task() {}

    public Task(TaskDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.estimatedTime = dto.getEstimatedTime();
        this.type = dto.getType();
        this.status = dto.getStatus();
        this.points = 0; // TO-DO IMPLEMENTAR ALGORITMO PARA CALCULAR COINS POR TASK
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
