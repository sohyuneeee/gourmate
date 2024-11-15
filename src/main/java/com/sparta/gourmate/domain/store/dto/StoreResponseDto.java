package com.sparta.gourmate.domain.store.dto;

import com.sparta.gourmate.domain.store.entity.Store;
import com.sparta.gourmate.global.common.BaseDto;
import lombok.Getter;

import java.util.UUID;

@Getter
public class StoreResponseDto extends BaseDto {
    private UUID storeId;
    private Long userId;
    private UUID categoryId;
    private String storeName;
    private String location;
    private double averageRating;


    public StoreResponseDto(Store store) {
        super(store);
        this.storeId = store.getId();
        this.userId = store.getUser().getId();
        this.categoryId = store.getCategory().getId();
        this.storeName = store.getName();
        this.location = store.getLocation();
        this.averageRating = store.getAverageRating();
    }
}
