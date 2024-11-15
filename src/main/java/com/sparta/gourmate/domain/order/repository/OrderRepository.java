package com.sparta.gourmate.domain.order.repository;

import com.sparta.gourmate.domain.order.entity.Order;
import com.sparta.gourmate.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Optional<Order> findByIdAndIsDeletedFalse(UUID id);
}
