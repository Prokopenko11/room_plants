package com.example.suns.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Today_temperature {

    @Id
    private int id;

    private Double temperature;

    private String temperature_site = "##:##";

    public Today_temperature() {
    }

    public Today_temperature(String timestamp_, Double temperature) {
        this.temperature = temperature;
    }

    public int getId() {
        return id;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getTemperature_site() {
        return temperature_site;
    }

    public void setTemperature_site(String temperature_site) {
        this.temperature_site = temperature_site;
    }
}
