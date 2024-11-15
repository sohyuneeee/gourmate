package com.sparta.gourmate.domain.store.dto;

import com.sparta.gourmate.domain.store.entity.Category;
import com.sparta.gourmate.global.common.BaseDto;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CategoryResponseDto extends BaseDto {
    private final UUID id;
    private final String name;

    public CategoryResponseDto(Category category) {
        super(category);
        this.id = category.getId();
        this.name = category.getName();
    }
}
