package com.sparta.gourmate.domain.review.service;

import com.sparta.gourmate.domain.order.entity.Order;
import com.sparta.gourmate.domain.order.repository.OrderRepository;
import com.sparta.gourmate.domain.review.dto.ReviewRequestDto;
import com.sparta.gourmate.domain.review.dto.ReviewResponseDto;
import com.sparta.gourmate.domain.review.entity.Review;
import com.sparta.gourmate.domain.review.repository.ReviewRepository;
import com.sparta.gourmate.domain.store.entity.Store;
import com.sparta.gourmate.domain.store.repository.StoreRepository;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.global.exception.CustomException;
import com.sparta.gourmate.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;

    // 리뷰 생성
    public ReviewResponseDto createReview(ReviewRequestDto requestDto, User user) {
        Store store = checkStore(requestDto.getStoreId());  // 가게 확인
        Order order = checkOrder(requestDto.getOrderId());  // 주문 확인

        Review review = new Review(requestDto, user, store, order);
        reviewRepository.save(review);
        updateRating(store, review);
        return new ReviewResponseDto(review);
    }

    // 리뷰 목록 조회
    public List<ReviewResponseDto> getReviewList(User user) {
        Long userId = user.getId();
        List<Review> reviewList = reviewRepository.findAllByUserIdAndIsDeletedFalse(userId);   // 내가 작성한 리뷰 조회

        List<ReviewResponseDto> responseDtoList = new ArrayList<>();

        for (Review review : reviewList) {
            responseDtoList.add(new ReviewResponseDto(review));
        }
        return responseDtoList;
    }

    // 리뷰 조회
    public ReviewResponseDto getReview(UUID reviewId) {
        Review review = checkReview(reviewId);  // 리뷰 확인

        return new ReviewResponseDto(review);
    }

    // 리뷰 수정
    @Transactional
    public ReviewResponseDto updateReview(UUID reviewId, ReviewRequestDto requestDto, User user) {
        Review review = checkReview(reviewId);  // 리뷰 확인
        checkUser(review, user);    // 유저 확인

        review.update(requestDto);

        return new ReviewResponseDto(review);
    }

    // 리뷰 삭제
    @Transactional
    public void deleteReview(UUID reviewId, User user) {
        Review review = checkReview(reviewId);  // 리뷰 확인
        checkUser(review, user);    // 유저 확인
        review.delete(user.getId());
    }

    // 첫 리뷰가 작성되면 가게 평점 업데이트
    private void updateRating(Store store, Review review) {
        long count = reviewRepository.countByStoreIdAndIsDeletedFalse(store.getId());

        if (count == 1) {
            store.updateAvg(review.getRating());
            storeRepository.save(store);
        }
    }

    // 유저 확인
    private void checkUser(Review review, User user) {
        Long userId = review.getUser().getId();
        if (!Objects.equals(userId, user.getId())) {
            throw new CustomException(ErrorCode.AUTH_AUTHORIZATION_FAILED);
        }
    }

    // 리뷰 확인
    private Review checkReview(UUID reviewId) {
        return reviewRepository.findByIdAndIsDeletedFalse(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
    }

    // 주문 확인
    private Order checkOrder(UUID orderId) {
        Order order = orderRepository.findByIdAndIsDeletedFalse(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        if (reviewRepository.findByOrderIdAndIsDeletedFalse(orderId).isPresent()) {
            throw new CustomException(ErrorCode.REVIEW_ALREADY_WROTE);
        }

        if (!Objects.equals(order.getOrderStatus(), "CONFIRMED")) {
            throw new CustomException(ErrorCode.ORDER_NOT_CONFIRMED);
        }

        return order;
    }

    // 가게 확인
    private Store checkStore(UUID storeId) {
        return storeRepository.findByIdAndIsDeletedFalse(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));
    }
}