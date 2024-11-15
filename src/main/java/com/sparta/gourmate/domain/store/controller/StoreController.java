package com.sparta.gourmate.domain.store.controller;

import com.sparta.gourmate.domain.review.dto.ReviewResponseDto;
import com.sparta.gourmate.domain.store.dto.StoreRequestDto;
import com.sparta.gourmate.domain.store.dto.StoreResponseDto;
import com.sparta.gourmate.domain.store.service.StoreService;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    // 가게 생성
    @PostMapping
    public ResponseEntity<StoreResponseDto> createStore(@Valid @RequestBody StoreRequestDto requestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        StoreResponseDto responseDto = storeService.createStore(requestDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 가게 목록 조회
    @GetMapping
    public Page<StoreResponseDto> getStoreList(@RequestParam(defaultValue = "") String query,
                                               @RequestParam(value = "categoryId", defaultValue = "") UUID categoryId,
                                               @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                                               @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc,
                                               @RequestParam(value = "page", defaultValue = "1") int page,
                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        return storeService.getStoreList(query, categoryId, sortBy, isAsc, page - 1, size);
    }

    // 가게 조회
    @GetMapping("/{storeId}")
    public StoreResponseDto getStore(@PathVariable UUID storeId) {
        return storeService.getStore(storeId);
    }

    // 가게 리뷰 조회
    @GetMapping("/{storeId}/reviews")
    public Page<ReviewResponseDto> getReviewList(@PathVariable UUID storeId,
                                                 @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                                                 @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc,
                                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "size", defaultValue = "10") int size) {
        return storeService.getReviewList(storeId, sortBy, isAsc, page - 1, size);
    }

    // 가게 수정
    @PutMapping("/{storeId}")
    public StoreResponseDto updateStore(@PathVariable UUID storeId,
                                        @Valid @RequestBody StoreRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return storeService.updateStore(storeId, requestDto, user);
    }

    // 가게 삭제
    @DeleteMapping("{storeId}")
    public void deleteStore(@PathVariable UUID storeId,
                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        storeService.deleteStore(storeId, user);
    }
}