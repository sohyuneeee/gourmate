package com.sparta.gourmate.domain.ai.controller;

import com.sparta.gourmate.domain.ai.dto.GoogleAiRequestDto;
import com.sparta.gourmate.domain.ai.dto.GoogleAiResponseDto;
import com.sparta.gourmate.domain.ai.service.GoogleAiService;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
public class GoogleAiController {

    private final GoogleAiService googleAiService;

    @PostMapping()
    public ResponseEntity<GoogleAiResponseDto> recommendText(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                             @Valid @RequestBody GoogleAiRequestDto requestDto) {
        User user = userDetails.getUser();
        GoogleAiResponseDto responseDto = googleAiService.recommendText(user, requestDto);
        return ResponseEntity.ok(responseDto);
    }

}
