package com.finalproject.ares.repositories;

import com.finalproject.ares.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo  extends JpaRepository<Category, Integer> {
}
