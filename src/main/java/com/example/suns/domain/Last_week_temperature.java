package com.example.suns.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Last_week_temperature {

    @Id
    private String day_of_week;

    private LocalDate date_;

    private double av_temperature;

    public Last_week_temperature() {
    }

    public Last_week_temperature(String day_of_week, LocalDate date_, double av_temperature) {
        this.day_of_week = day_of_week;
        this.date_ = date_;
        this.av_temperature = av_temperature;
    }

    public String getDay_of_week() {
        return day_of_week;
    }

    public void setDay_of_week(String day_of_week) {
        this.day_of_week = day_of_week;
    }

    public LocalDate getDate_() {
        return date_;
    }

    public void setDate_(LocalDate date_) {
        this.date_ = date_;
    }

    public double getAv_temperature() {
        return av_temperature;
    }

    public void setAv_temperature(double av_temperature) {
        this.av_temperature = av_temperature;
    }
}
