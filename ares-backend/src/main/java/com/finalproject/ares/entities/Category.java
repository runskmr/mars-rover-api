package com.finalproject.ares.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "favoriteCategory")
    private Set<Favorite> categoryFavorites;

    public Category() {}
    public Category(Set<Favorite> categoryFavorites) {
        this.categoryFavorites = categoryFavorites;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
