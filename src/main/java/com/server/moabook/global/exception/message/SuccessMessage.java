package com.server.moabook.global.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessMessage {

    SIGNUP_SUCCESS(HttpStatus.CREATED.value(),"회원가입에 성공하였습니다."),
    SIGNIN_SUCCESS(HttpStatus.CREATED.value(),"로그인에 성공하였습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK.value(),"로그아웃에 성공하였습니다."),
    ACCOUNT_DELETION_SUCCESS(HttpStatus.OK.value(),"회원 탈퇴에 성공하였습니다."),
    MYPAGE_GET_SUCCESS(HttpStatus.OK.value(),"마이페이지 조회에 성공하였습니다."),
    ACTIVITY_LIKE_READ_SUCCESS(HttpStatus.OK.value(), "액티비티 찜 목록 조회에 성공하였습니다."),
    ACTIVITY_LIKE_ADD_SUCCESS(HttpStatus.CREATED.value(), "액티비티 찜에 성공하였습니다."),
    ACTIVITY_LIKE_DELETE_SUCCESS(HttpStatus.OK.value(), "액티비티 찜 취소에 성공하였습니다."),
    RESERVE_ACTIVITY_READ_SUCCESS(HttpStatus.OK.value(), "예약 액티비티 목록 조회에 성공하였습니다."),
    COMPLETED_ACTIVITY_READ_SUCCESS(HttpStatus.OK.value(), "체험 완료한 액티비티 목록 조회에 성공하였습니다."),
    RANK_GET_SUCCESS(HttpStatus.OK.value(), "사용자 등급 조회에 성공하였습니다."),
    REVIEW_ADD_SUCCESS(HttpStatus.OK.value(), "리뷰 등록에 성공하였습니다."),
    CHALLENGE_READ_SUCCESS(HttpStatus.OK.value(), "오늘의 챌린지 조회에 성공하였습니다."),
    CHALLENGE__HISTORY_READ_SUCCESS(HttpStatus.OK.value(), "사용자의 지금까지 챌린지 현황 조회에 성공하였습니다."),
    NICKNAME_CHANGE_SUCCESS(HttpStatus.OK.value(), "닉네임 변경에 성공하였습니다."),
    RESERVE_ACTIVITY_RESERVE_SUCCESS(HttpStatus.OK.value(), "액티비티 예약에 성공하였습니다."),
    RESERVE_ACTIVITY_DELETE_SUCCESS(HttpStatus.OK.value(), "예약 삭제에 성공하였습니다."),
    CART_ITEM_ADD_SUCCESS(HttpStatus.OK.value(), "장바구니 추가에 성공하였습니다."),
    CART_ITEM_LIST_SUCCESS(HttpStatus.OK.value(), "장바구니 조회에 성공하였습니다."),
    CART_DELETE_SUCCESS(HttpStatus.OK.value(), "장바구니 삭제에 성공하였습니다."),
    CART_ITEM_DELETE_SUCCESS(HttpStatus.OK.value(), "장바구니 항목 삭제에 성공하였습니다."),
    ACTIVITY_TYPE_READ_SUCCESS(HttpStatus.OK.value(), "타입에 맞는 인기 액티비티 조회에 성공하였습니다."),
    ACTIVITY_POPULAR_READ_SUCCESS(HttpStatus.OK.value(), "인기 액티비티 조회에 성공하였습니다."),
    CHALLENGE_PROOF_SUCCESS(HttpStatus.OK.value(), "챌린지 사진 등록에 성공하였습니다."),
    ADMIN_CHALLENGE_PROOF_SUCCESS(HttpStatus.OK.value(), "관리자 챌린지 인증에 성공하였습니다."),
    ADMIN_CHALLENGE_REGISTER_SUCCESS(HttpStatus.OK.value(), "관리자 챌린지 등록에 성공하였습니다."),
    ADMIN_ACTIVITY_REGISTER_SUCCESS(HttpStatus.OK.value(), "관리자 액티비티 등록에 성공하였습니다."),
    ACTIVITY_READ_SUCCESS(HttpStatus.OK.value(), "액티비티 상세 조회에 성공하였습니다."),
    ACTIVITY_MAINCATEGORY_RECOMMEND_READ_SUCCESS(HttpStatus.OK.value(), "메인 카테고리 추천 액티비티 조회에 성공하였습니다."),;
    private final int code;
    private final String message;
}