package com.FinalProject.repository;

import com.FinalProject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByDeleteStatusIsFalseOrderByIdDesc();
    List<Category> findCategoriesByNameContainingIgnoreCase(String name);

    Optional<Category> findCategoriesByName(String name);
}
