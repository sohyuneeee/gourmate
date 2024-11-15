package com.sparta.gourmate.domain.ai.dto;

import com.sparta.gourmate.domain.ai.entity.GoogleAi;
import com.sparta.gourmate.global.common.BaseDto;
import lombok.Getter;

import java.util.UUID;

@Getter
public class GoogleAiResponseDto extends BaseDto {
    private final UUID id;
    private final String text;

    public GoogleAiResponseDto(GoogleAi googleAi) {
        super(googleAi);
        this.id = googleAi.getId();
        this.text = googleAi.getResponse();
    }

}
