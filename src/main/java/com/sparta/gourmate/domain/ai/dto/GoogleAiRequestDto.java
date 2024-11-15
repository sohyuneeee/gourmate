package com.sparta.gourmate.domain.ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleAiRequestDto {
    @NotBlank(message = "AI_TEXT_EMPTY")
    @Size(min = 2, max = 150, message = "AI_TEXT_LENGTH_INVALID")
    private String text;

}
