package com.sparta.gourmate.global.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BaseDto {

    private Long createdBy;
    private LocalDateTime createdAt;
    private Long updatedBy;
    private LocalDateTime updatedAt;
    private Long deletedBy;
    private LocalDateTime deletedAt;

    public BaseDto(BaseEntity baseEntity) {
        this.createdBy = baseEntity.getCreatedBy();
        this.createdAt = baseEntity.getCreatedAt();
        this.updatedBy = baseEntity.getUpdatedBy();
        this.updatedAt = baseEntity.getUpdatedAt();
        this.deletedBy = baseEntity.getDeletedBy();
        this.deletedAt = baseEntity.getDeletedAt();
    }
}
