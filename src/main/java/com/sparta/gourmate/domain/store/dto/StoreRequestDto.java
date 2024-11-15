package com.sparta.gourmate.domain.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class StoreRequestDto {
    @NotNull(message = "CATEGORY_NOT_EXISTS")
    private UUID categoryId;

    @NotBlank(message = "STORE_NAME_EMPTY")
    private String name;

    @NotBlank(message = "STORE_LOCATION_EMPTY")
    private String location;

}
