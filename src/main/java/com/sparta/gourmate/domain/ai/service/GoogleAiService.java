package com.sparta.gourmate.domain.ai.service;

import com.sparta.gourmate.domain.ai.dto.GoogleAiRequestDto;
import com.sparta.gourmate.domain.ai.dto.GoogleAiResponseDto;
import com.sparta.gourmate.domain.ai.entity.GoogleAi;
import com.sparta.gourmate.domain.ai.repository.GoogleAiRepository;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.global.exception.CustomException;
import com.sparta.gourmate.global.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j(topic = "Google AI API")
@Service
public class GoogleAiService {

    private final GoogleAiRepository googleAiRepository;
    private final RestTemplate restTemplate;
    @Value("${google.ai.api.key}")
    private String apiKey;

    public GoogleAiService(GoogleAiRepository googleAiRepository, RestTemplateBuilder builder) {
        this.googleAiRepository = googleAiRepository;
        this.restTemplate = builder.build();
    }

    @Retryable(
            //재시도 대상 예외 명시: 서버 오류 및 연결/타임아웃 오류 시 재시도
            value = {HttpServerErrorException.class, ResourceAccessException.class},
            //재시도 횟수
            maxAttempts = 3,
            //초기 딜레이 2초, 재시도마다 딜레이 2배씩 증가 (2초 -> 4초 -> 8초)
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public GoogleAiResponseDto recommendText(User user, GoogleAiRequestDto requestDto) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://generativelanguage.googleapis.com")
                .path("/v1beta/models/gemini-1.5-flash-latest:generateContent")
                .queryParam("key", apiKey)
                .encode()
                .build()
                .toUri();
        log.info("uri = " + uri);

        String text = requestDto.getText() + " 답변을 최대한 간결하게 50자 이내로 해줘.";
        log.info("text = " + text);

        String requestBody = String.format("{ \"contents\": [{\"parts\": [{\"text\": \"%s\"}]}] }", text);

        RequestEntity<String> requestEntity = RequestEntity
                .post(uri)
                .header("Content-Type", "application/json")
                .body(requestBody);

       ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
       log.info("API Status Code: " + responseEntity.getStatusCode());
       String responseText = parsingTextFromResponse(responseEntity.getBody());
       GoogleAi googleAi = googleAiRepository.save(new GoogleAi(responseText, user));
       return new GoogleAiResponseDto(googleAi);

    }

    private String parsingTextFromResponse(String responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody);
            String text = jsonObject
                    .getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");
            log.info(text);
            return text;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.AI_RESPONSE_PARSING_ERROR);
        }
    }

    //재시도 횟수 소진 시 실행 되는 메소드
    @Recover
    public GoogleAiResponseDto recover(HttpServerErrorException e, User user, GoogleAiRequestDto requestDto) {
        log.error("Server error occurred after retries: {}", e.getMessage());
        throw new CustomException(ErrorCode.AI_EXTERNAL_API_ERROR);
    }

    @Recover
    public GoogleAiResponseDto recover(ResourceAccessException e, User user, GoogleAiRequestDto requestDto) {
        log.error("Timeout error occurred after retries: {}", e.getMessage());
        throw new CustomException(ErrorCode.AI_TIMEOUT_ERROR);
    }

}
