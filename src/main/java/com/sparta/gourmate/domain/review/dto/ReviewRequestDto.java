package com.sparta.gourmate.domain.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ReviewRequestDto {
    @NotNull(message = "ORDER_NOT_EXISTS")
    private UUID orderId;

    @NotNull(message = "STORE_NOT_EXISTS")
    private UUID storeId;

    @NotNull(message = "REVIEW_RATING_EMPTY")
    private int rating;

    private String content;
}
