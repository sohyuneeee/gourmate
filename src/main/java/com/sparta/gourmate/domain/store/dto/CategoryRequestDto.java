package com.sparta.gourmate.domain.store.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CategoryRequestDto {
    @NotBlank(message = "CATEGORY_NAME_EMPTY")
    private String name;
}
