package com.server.moabook.core.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorMessage {

    JWT_UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED.value(), "사용자의 로그인 검증을 실패했습니다."),
    INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 accessToken입니다."),
    GROUP_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 그룹을 찾을 수 없습니다."),
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 책을 찾을 수 없습니다."),
    PAGE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 페이지를 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "사용자를 찾을 수 없습니다."),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST.value(), "이메일 형식이 올바르지 않습니다."),;
    private final int code;
    private final String message;
}
