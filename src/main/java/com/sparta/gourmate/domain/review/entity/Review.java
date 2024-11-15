package com.sparta.gourmate.domain.review.entity;

import com.sparta.gourmate.domain.order.entity.Order;
import com.sparta.gourmate.domain.review.dto.ReviewRequestDto;
import com.sparta.gourmate.domain.store.entity.Store;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "p_reviews")
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private int rating;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    public Review(ReviewRequestDto requestDto, User user, Store store, Order order) {
        this.rating = requestDto.getRating();
        this.content = requestDto.getContent();
        this.user = user;
        this.order = order;
        this.store = store;
    }

    public void update(ReviewRequestDto requestDto) {
        this.rating = requestDto.getRating();
        this.content = requestDto.getContent();
        this.user = user;
        this.order = order;
        this.store = store;
    }
}
