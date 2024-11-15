package com.sparta.gourmate.domain.store.repository;

import com.sparta.gourmate.domain.store.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    @Query("SELECT c FROM Category c WHERE c.isDeleted = false")
    List<Category> findAllAndIsDeletedFalse();

    Optional<Category> findByIdAndIsDeletedFalse(UUID id);
}
