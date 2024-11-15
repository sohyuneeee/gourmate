package com.sparta.gourmate.domain.store.service;

import com.sparta.gourmate.domain.store.dto.CategoryRequestDto;
import com.sparta.gourmate.domain.store.dto.CategoryResponseDto;
import com.sparta.gourmate.domain.store.entity.Category;
import com.sparta.gourmate.domain.store.repository.CategoryRepository;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.domain.user.entity.UserRoleEnum;
import com.sparta.gourmate.global.exception.CustomException;
import com.sparta.gourmate.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 카테고리 생성
    public CategoryResponseDto createCategory(CategoryRequestDto requestDto, User user) {
        checkRole(user);    // 권한 확인

        Category category = new Category(requestDto);
        categoryRepository.save(category);
        return new CategoryResponseDto(category);
    }

    // 카테고리 목록 조회
    public List<CategoryResponseDto> getCategories() {
        List<Category> categoryList = categoryRepository.findAllAndIsDeletedFalse();
        List<CategoryResponseDto> responseDtoList = new ArrayList<>();

        for (Category category : categoryList) {
            responseDtoList.add(new CategoryResponseDto(category));
        }
        return responseDtoList;
    }

    // 카테고리 수정
    @Transactional
    public CategoryResponseDto updateCategory(UUID categoryId, CategoryRequestDto requestDto, User user) {
        checkRole(user);    // 권한 확인
        Category category = checkCategory(categoryId);  // 카테고리 확인

        category.update(requestDto);

        return new CategoryResponseDto(category);
    }

    // 카테고리 삭제
    @Transactional
    public void deleteCategory(UUID categoryId, User user) {
        checkRole(user);    // 권한 확인
        Category category = checkCategory(categoryId);  // 카테고리 확인

        category.delete(user.getId());
    }

    // 카테고리 확인
    private Category checkCategory(UUID categoryId) {
        return categoryRepository.findByIdAndIsDeletedFalse(categoryId).orElseThrow(
                () -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    // 권한 확인
    private void checkRole(User user) {
        UserRoleEnum role = user.getRole();

        if (!role.isAdmin()) {
            throw new CustomException(ErrorCode.AUTH_AUTHORIZATION_FAILED);
        }
    }
}