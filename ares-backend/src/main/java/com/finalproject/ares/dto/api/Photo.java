package com.finalproject.ares.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Photo {
    private int id;
    private int sol;
    private Camera camera;

    @JsonProperty("img_src")
    private String imgSrc;

    @JsonProperty("earth_date")
    private LocalDate earthDate;
    private Rover rover;

    public Photo() {}

    public Photo(int id, int sol, Camera camera, String imgSrc, LocalDate earthDate, Rover rover) {
        this.id = id;
        this.sol = sol;
        this.camera = camera;
        this.imgSrc = imgSrc;
        this.earthDate = earthDate;
        this.rover = rover;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSol() {
        return sol;
    }

    public void setSol(int sol) {
        this.sol = sol;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public LocalDate getEarthDate() {
        return earthDate;
    }

    public void setEarthDate(LocalDate earthDate) {
        this.earthDate = earthDate;
    }

    public Rover getRover() {
        return rover;
    }

    public void setRover(Rover rover) {
        this.rover = rover;
    }
}
