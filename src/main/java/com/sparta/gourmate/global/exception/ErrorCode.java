package com.sparta.gourmate.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    COMMON_INVALID_PARAMETER("잘못된 파라미터입니다.", BAD_REQUEST),
    COMMON_SERVER_ERROR("서버에서 에러가 발생하였습니다.", INTERNAL_SERVER_ERROR),
    AUTH_AUTHENTICATION_FAILED("인증에 실패하셨습니다.", UNAUTHORIZED),
    AUTH_AUTHORIZATION_FAILED("권한이 없습니다.", FORBIDDEN),
    AUTH_JWT_CLAIMS_EMPTY("JWT claims 문자열이 비어 있습니다.", UNAUTHORIZED),
    AUTH_JWT_EXPIRED("만료된 토큰입니다.", UNAUTHORIZED),
    AUTH_JWT_INVALID("잘못된 토큰입니다.", UNAUTHORIZED),
    AUTH_JWT_UNSUPPORTED("지원되지 않는 토큰입니다.", UNAUTHORIZED),
    AUTH_JWT_UNPRIVILEGED("권한이 없는 토큰입니다.", FORBIDDEN),
    INVALID_PASSWORD("비밀번호가 잘못됐습니다.", FORBIDDEN),
    USER_NOT_EXISTS("존재하지 않는 사용자입니다.", BAD_REQUEST),
    USERNAME_POLICY("이름은 영어소문자, 숫자로 이루어진 4자이상 10자 이하의 문자열이어야 합니다.", BAD_REQUEST),
    PASSWORD_POLICY("비밀번호는 영어대소문자, 숫자, 특수문자로 이루어진 8자 이상 15자 이하의 문자열이어야 합니다.", BAD_REQUEST),
    USER_DUPLICATED("중복된 이름입니다.", CONFLICT),
    EMAIL_DUPLICATED("중복된 이메일입니다.", CONFLICT),

    USER_NOT_SAME("해당 작성자가 아닙니다.", BAD_REQUEST),

    STORE_NOT_EXISTS("가게Id가 존재하지 않습니다.", BAD_REQUEST),
    STORE_NOT_FOUND("가게가 존재하지 않습니다.", NOT_FOUND),

    MENU_NOT_FOUND("메뉴가 존재하지 않습니다.", NOT_FOUND),
    MENU_NAME_EMPTY("메뉴 이름이 존재하지 않습니다.", BAD_REQUEST),
    MENU_PRICE_EMPTY("메뉴 가격이 존재하지 않습니다.", BAD_REQUEST),
    MENU_PRICE_INVALID("메뉴 가격이 유효하지 않습니다.", BAD_REQUEST),
    MENU_STATUS_EMPTY("메뉴 상태가 존재하지 않습니다.", BAD_REQUEST),
    MENU_STATUS_INVALID("메뉴 상태 값이 유효하지 않습니다.", BAD_REQUEST),

    AI_TEXT_EMPTY("내용 값이 존재하지 않습니다.", BAD_REQUEST),
    AI_TEXT_LENGTH_INVALID("내용 값은 최소 2자, 최대 50자 입니다.", BAD_REQUEST),
    AI_EXTERNAL_API_ERROR("외부 API 호출 중 서버 오류가 발생하였습니다.", INTERNAL_SERVER_ERROR),
    AI_INVALID_REQUEST("잘못된 요청입니다.", BAD_REQUEST),
    AI_TIMEOUT_ERROR("요청이 시간 초과되었습니다.", REQUEST_TIMEOUT),
    AI_RESPONSE_PARSING_ERROR("JSON 응답 데이터 파싱 중 오류가 발생하였습니다.", INTERNAL_SERVER_ERROR),

    CATEGORY_NOT_FOUND("카테고리가 존재하지 않습니다.", NOT_FOUND),
    CATEGORY_NAME_EMPTY("카테고리 이름이 존재하지 않습니다.", BAD_REQUEST),

    ORDER_NOT_EXISTS("주문Id가 존재하지 않습니다.", BAD_REQUEST),
    ORDER_NOT_FOUND("주문이 존재하지 않습니다.", NOT_FOUND),
    ORDER_NOT_CONFIRMED("주문이 완료되지 않았습니다.", BAD_REQUEST),

    REVIEW_RATING_EMPTY("리뷰 별점 값이 유효하지 않습니다.", BAD_REQUEST),
    REVIEW_NOT_FOUND("리뷰가 존재하지 않습니다.", NOT_FOUND),
    REVIEW_ALREADY_WROTE("리뷰가 이미 작성되어 있습니다.", BAD_REQUEST)

    ;

    private final String message;
    private final HttpStatus httpStatus;

}
