package com.sparta.gourmate.domain.menu.dto;

import com.sparta.gourmate.domain.menu.entity.MenuStatusEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuUpdateRequestDto {

    private String menuName;

    private String description;

    @Positive(message = "MENU_PRICE_INVALID")
    private Integer price;

    @NotNull(message = "MENU_STATUS_INVALID")
    private MenuStatusEnum status;

}
