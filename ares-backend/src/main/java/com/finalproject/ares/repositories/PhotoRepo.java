package com.finalproject.ares.repositories;

import com.finalproject.ares.entities.Favorite;
import com.finalproject.ares.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepo  extends JpaRepository<Photo, Integer> {
}
