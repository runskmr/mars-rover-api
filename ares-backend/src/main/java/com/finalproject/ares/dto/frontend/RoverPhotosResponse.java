package com.finalproject.ares.dto.frontend;

import java.util.List;

public class RoverPhotosResponse {

    private String roverName;

    private List<PhotoResponse> roverPhotos;

    public RoverPhotosResponse() {}

    public RoverPhotosResponse(String name, List<PhotoResponse> roverPhotos) {
        this.roverName = name;
        this.roverPhotos = roverPhotos;
    }

    public String getRoverName() {
        return roverName;
    }

    public void setRoverName(String roverName) {
        this.roverName = roverName;
    }

    public List<PhotoResponse> getRoverPhotos() {
        return roverPhotos;
    }

    public void setRoverPhotos(List<PhotoResponse> roverPhotos) {
        this.roverPhotos = roverPhotos;
    }
}
