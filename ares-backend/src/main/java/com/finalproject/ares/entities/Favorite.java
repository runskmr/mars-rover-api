package com.finalproject.ares.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "favorite")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "categoryid")
    private Category favoriteCategory;

    @ManyToOne
    @JoinColumn(name = "photoid")
    private Photo favoritePhoto;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User userFavorite;

    public Favorite() {}

    public Favorite(Category favoriteCategory, Photo favoritePhoto, User userFavorite) {
        this.favoriteCategory = favoriteCategory;
        this.favoritePhoto = favoritePhoto;
        this.userFavorite = userFavorite;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Category getFavoriteCategory() {
        return favoriteCategory;
    }

    public void setFavoriteCategory(Category favoriteCategory) {
        this.favoriteCategory = favoriteCategory;
    }

    public Photo getFavoritePhoto() {
        return favoritePhoto;
    }

    public void setFavoritePhoto(Photo favoritePhoto) {
        this.favoritePhoto = favoritePhoto;
    }

    public User getUserFavorite() {
        return userFavorite;
    }

    public void setUserFavorite(User userFavorite) {
        this.userFavorite = userFavorite;
    }
}
