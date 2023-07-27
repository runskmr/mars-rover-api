package com.finalproject.ares.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "photo")
public class Photo {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "imgsrc", nullable = false)
    private String imgSrc;
    @Column(name = "camera", nullable = false)
    private String camera;
    @Column(name = "rover", nullable = false)
    private String rover;
    @OneToMany(mappedBy = "favoritePhoto")
    private Set<Favorite> photoFavorites;

    public Photo() {}

    public Photo(Set<Favorite> photoFavorites) {
        this.photoFavorites = photoFavorites;
    }

    public Photo(com.finalproject.ares.dto.api.Photo apiPhoto) {
        this.id = apiPhoto.getId();
        this.imgSrc = apiPhoto.getImgSrc();
        this.camera = apiPhoto.getCamera().getName();
        this.rover = apiPhoto.getRover().getName();
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

    public String getRover() {
        return rover;
    }

    public void setRover(String rover) {
        this.rover = rover;
    }
}
