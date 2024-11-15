package com.sparta.gourmate.domain.store.controller;

import com.sparta.gourmate.domain.store.dto.CategoryRequestDto;
import com.sparta.gourmate.domain.store.dto.CategoryResponseDto;
import com.sparta.gourmate.domain.store.service.CategoryService;
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
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    // 카테고리 생성
    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@Valid @RequestBody CategoryRequestDto requestDto,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        CategoryResponseDto responseDto = categoryService.createCategory(requestDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 카테고리 목록 조회
    @GetMapping
    public List<CategoryResponseDto> getCategories() {
        return categoryService.getCategories();
    }

    // 카테고리 수정
    @PutMapping("/{categoryId}")
    public CategoryResponseDto updateCategory(@PathVariable UUID categoryId,
                                              @Valid @RequestBody CategoryRequestDto requestDto,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return categoryService.updateCategory(categoryId, requestDto, user);
    }

    // 카테고리 삭제
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable UUID categoryId,
                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        categoryService.deleteCategory(categoryId, user);
    }
}