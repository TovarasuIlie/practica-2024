package com.PracticaVara.springJwt.repository;

import com.PracticaVara.springJwt.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findBySearchLink(String url);
}

