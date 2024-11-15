package com.sparta.gourmate.domain.store.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class AvgResponseDto {
    private final UUID storeId;
    private final double avg;

    public AvgResponseDto(UUID storeId, double avg) {
        this.storeId = storeId;
        this.avg = avg;
    }
}
