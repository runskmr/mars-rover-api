package com.finalproject.ares.dto.frontend;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finalproject.ares.dto.api.Photo;

import java.time.LocalDate;

public class PhotoResponse {
    private int id;

    private String imgSrc;
    private String camera;

    private LocalDate date;
    private String rover;
    private boolean isFavorite;
    private String category;

    public PhotoResponse() {}

    public PhotoResponse(int id, String imgSrc, String camera, LocalDate date, String rover, boolean isFavorite) {
        this.id = id;
        this.imgSrc = imgSrc;
        this.camera = camera;
        this.date = date;
        this.rover = rover;
        this.isFavorite = isFavorite;
    }

    public PhotoResponse(Photo ApiPhoto) {
        this.id = ApiPhoto.getId();
        this.imgSrc = ApiPhoto.getImgSrc();
        this.camera = ApiPhoto.getCamera().getName();
        this.date = ApiPhoto.getEarthDate();
        this.rover = ApiPhoto.getRover().getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getRover() {
        return rover;
    }

    public void setRover(String rover) {
        this.rover = rover;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
