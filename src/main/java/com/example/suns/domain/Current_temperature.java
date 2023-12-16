package com.example.suns.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class Current_temperature {

    @Id
    private LocalDateTime timestamp_;

    private double temperature;

    private String time;

    private String month;

    private String day_of_month;

    public Current_temperature() {
    }

    public Current_temperature(LocalDateTime timestamp_, double temperature) {
        this.timestamp_ = timestamp_;
        this.temperature = temperature;
    }

    public LocalDateTime getTimestamp_() {
        return timestamp_;
    }

    public void setTimestamp_(LocalDateTime timestamp_) {
        this.timestamp_ = timestamp_;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay_of_month() {
        return day_of_month;
    }

    public void setDay_of_month(String day_of_month) {
        this.day_of_month = day_of_month;
    }
}
