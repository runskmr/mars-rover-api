package com.finalproject.ares.dto.api;

import java.util.List;

public class Photos {
    private List<Photo> photos;

    public Photos() {
    }

    public Photos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
