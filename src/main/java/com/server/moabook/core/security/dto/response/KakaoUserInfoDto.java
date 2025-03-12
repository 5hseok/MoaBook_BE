package com.server.moabook.core.security.dto.response;

public record KakaoUserInfoDto(Long id, String email, String nickname, String profileImageUrl) {

    public static KakaoUserInfoDto of(Long id, String email, String nickname, String profileImageUrl) {
        return new KakaoUserInfoDto(id, email, nickname, profileImageUrl);
    }
}