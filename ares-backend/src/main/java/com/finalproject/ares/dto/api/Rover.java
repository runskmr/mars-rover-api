package com.finalproject.ares.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Rover {
    private int id;
    private String name;

    @JsonProperty("landing_date")
    private LocalDate landingDate;

    @JsonProperty("launch_date")
    private LocalDate launchDate;
    private String status;

    public Rover() {
    }

    public Rover(int id, String name, LocalDate landingDate, LocalDate launchDate, String status) {
        this.id = id;
        this.name = name;
        this.landingDate = landingDate;
        this.launchDate = launchDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getLandingDate() {
        return landingDate;
    }

    public void setLandingDate(LocalDate landingDate) {
        this.landingDate = landingDate;
    }

    public LocalDate getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(LocalDate launchDate) {
        this.launchDate = launchDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
