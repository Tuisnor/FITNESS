package com.fitness.workout_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private int duration; 

    private String type; 

    private LocalDateTime date;

    public Workout() {
        this.date = LocalDateTime.now();
    }

    // Constructor completo (opcional)
    public Workout(Long userId, int duration, String type, LocalDateTime date) {
        this.userId = userId;
        this.duration = duration;
        this.type = type;
        this.date = date;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id=" + id +
                ", userId=" + userId +
                ", duration=" + duration +
                ", type='" + type + '\'' +
                ", date=" + date +
                '}';
    }
}
