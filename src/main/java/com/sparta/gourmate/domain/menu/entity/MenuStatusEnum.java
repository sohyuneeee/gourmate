package com.sparta.gourmate.domain.menu.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MenuStatusEnum {
    AVAILABLE("AVAILABLE"),
    OUT_OF_STOCK("OUT_OF_STOCK"),
    HIDDEN("HIDDEN");

    private final String MenuStatus;

    @JsonCreator
    public static MenuStatusEnum from(String value) {
        for (MenuStatusEnum status : MenuStatusEnum.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        return null;
    }

}
