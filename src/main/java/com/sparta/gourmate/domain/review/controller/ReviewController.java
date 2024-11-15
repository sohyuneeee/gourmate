package com.sparta.gourmate.domain.review.controller;

import com.sparta.gourmate.domain.review.dto.ReviewRequestDto;
import com.sparta.gourmate.domain.review.dto.ReviewResponseDto;
import com.sparta.gourmate.domain.review.service.ReviewService;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(@Valid @RequestBody ReviewRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        ReviewResponseDto responseDto = reviewService.createReview(requestDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 리뷰 목록 조회
    @GetMapping
    public List<ReviewResponseDto> getReviewList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return reviewService.getReviewList(user);
    }

    // 리뷰 조회
    @GetMapping("/{reviewId}")
    public ReviewResponseDto getReview(@PathVariable UUID reviewId) {
        return reviewService.getReview(reviewId);
    }

    // 리뷰 수정
    @PutMapping("/{reviewId}")
    public ReviewResponseDto updateReview(@PathVariable UUID reviewId,
                                          @Valid @RequestBody ReviewRequestDto requestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return reviewService.updateReview(reviewId, requestDto, user);
    }

    // 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable UUID reviewId,
                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        reviewService.deleteReview(reviewId, user);
    }
}