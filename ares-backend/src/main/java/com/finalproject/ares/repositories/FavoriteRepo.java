package com.finalproject.ares.repositories;

import com.finalproject.ares.entities.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepo  extends JpaRepository<Favorite, Integer> {
    Favorite findByFavoritePhotoId(int id);
    void deleteByFavoritePhotoId(int id);
}
