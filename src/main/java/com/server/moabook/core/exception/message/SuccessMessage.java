package com.server.moabook.core.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessMessage {

    SIGNIN_SUCCESS(HttpStatus.CREATED.value(),"로그인에 성공하였습니다."),
    SELECT_USER_SUCCESS(HttpStatus.CREATED.value(),"유저 조회에 성공하였습니다."),
    DELETE_USER_SUCCESS(HttpStatus.CREATED.value(),"유저 삭제에 성공하였습니다."),
    DELETE_ALL_USER_SUCCESS(HttpStatus.CREATED.value(),"유저 기록 삭제에 성공하였습니다."),
    CREATE_GROUP_SUCCESS(HttpStatus.OK.value(),"그룹 생성을 성공하였습니다."),
    SELECT_GROUP_SUCCESS(HttpStatus.OK.value(),"그룹 조회를 성공하였습니다."),
    UPDATE_GROUP_SUCCESS(HttpStatus.OK.value(),"그룹 수정을 성공하였습니다."),
    DELETE_GROUP_SUCCESS(HttpStatus.OK.value(),"그룹 삭제를 성공하였습니다."),
    CREATE_BOOK_SUCCESS(HttpStatus.OK.value(),"책 생성을 성공하였습니다."),
    SELECT_BOOK_SUCCESS(HttpStatus.OK.value(),"책 조회를 성공하였습니다."),
    UPDATE_BOOK_SUCCESS(HttpStatus.OK.value(),"책 수정을 성공하였습니다."),
    DELETE_BOOK_SUCCESS(HttpStatus.OK.value(),"책 삭제를 성공하였습니다."),
    CREATE_PAGE_SUCCESS(HttpStatus.OK.value(),"페이지 생성을 성공하였습니다."),
    SAVE_PAGE_SUCCESS(HttpStatus.OK.value(),"페이지 저장을 성공하였습니다."),
    SELECT_PAGE_SUCCESS(HttpStatus.OK.value(),"페이지 조회를 성공하였습니다."),
    DELETE_PAGE_SUCCESS(HttpStatus.OK.value(),"페이지 삭제를 성공하였습니다."),
    UPDATE_RECEIVED_EMAIL_SUCCESS(HttpStatus.OK.value(), "이메일 수신 동의 여부가 반영되었습니다."),
    CREATE_TEXT_SUCCESS(HttpStatus.OK.value(), "텍스트 생성에 성공하였습니다."),
    SELECT_TEXT_SUCCESS(HttpStatus.OK.value(), "텍스트 조회에 성공하였습니다."),
    UPDATE_TEXT_SUCCESS(HttpStatus.OK.value(), "텍스트 수정에 성공하였습니다."),
    DELETE_TEXT_SUCCESS(HttpStatus.OK.value(), "텍스트 삭제에 성공하였습니다."),
    CREATE_URL_SUCCESS(HttpStatus.OK.value(), "URL 생성에 성공하였습니다."),
    HEALTH_CHECK_SUCCESS(HttpStatus.OK.value(), "서버 정상 작동 중입니다.");


    private final int code;
    private final String message;
}