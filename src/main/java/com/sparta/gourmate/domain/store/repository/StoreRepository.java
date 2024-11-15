package com.sparta.gourmate.domain.store.repository;

import com.sparta.gourmate.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID> {
    Optional<Store> findByIdAndIsDeletedFalse(UUID id);

    Page<Store> findByCategoryIdAndNameContainingAndIsDeletedFalse(UUID categoryId, String query, Pageable pageable);

    Page<Store> findByNameContainingAndIsDeletedFalse(String query, Pageable pageable);
}
