package com.sparta.gourmate.domain.review.repository;

import com.sparta.gourmate.domain.store.dto.AvgResponseDto;
import com.sparta.gourmate.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    Optional<Review> findByIdAndIsDeletedFalse(UUID id);

    Optional<Review> findByOrderIdAndIsDeletedFalse(UUID orderId);

    List<Review> findAllByUserIdAndIsDeletedFalse(Long userId);

    Page<Review> findAllByStoreIdAndIsDeletedFalse(UUID storeId, Pageable pageable);

    @Query("select new com.sparta.gourmate.domain.store.dto.AvgResponseDto(r.store.id, avg(r.rating)) from Review r where r.isDeleted = false group by r.store.id")
    List<AvgResponseDto> calculateAvg();

    long countByStoreIdAndIsDeletedFalse(UUID id);
}
