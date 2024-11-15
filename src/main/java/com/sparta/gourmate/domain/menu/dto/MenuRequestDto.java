package com.sparta.gourmate.domain.menu.dto;

import com.sparta.gourmate.domain.menu.entity.MenuStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class MenuRequestDto {

    @NotNull(message = "STORE_NOT_EXISTS")
    private UUID storeId;

    @NotBlank(message = "MENU_NAME_EMPTY")
    private String menuName;

    private String description;

    @NotNull(message = "MENU_PRICE_EMPTY")
    @Positive(message = "MENU_PRICE_INVALID")
    private Integer price;

    @NotNull(message = "MENU_STATUS_INVALID")
    private MenuStatusEnum status;

}
