package com.server.moabook.user.controller;

import com.server.moabook.core.exception.dto.SuccessStatusResponse;
import com.server.moabook.core.security.dto.response.SuccessLoginResponseDto;
import com.server.moabook.user.service.AuthService;
import com.server.moabook.core.security.dto.request.KakaoUserInfoRequestDto;
import com.server.moabook.core.exception.message.SuccessMessage;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    //카카오 로그인 메소드
    @PostMapping("/login/kakao")
    public ResponseEntity<SuccessStatusResponse<SuccessLoginResponseDto>> login(
            @RequestBody @NotNull KakaoUserInfoRequestDto userInfo
    ) {
        SuccessLoginResponseDto successLoginResponseDto = authService.kakaoAuthSocialLogin(userInfo);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessStatusResponse.of(
                        SuccessMessage.SIGNIN_SUCCESS, successLoginResponseDto
                )
        );
    }

    // 액세스 토큰 재발급 및 리프레시 토큰 업데이트 API
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(
            @NotNull @RequestHeader("Authorization") String refreshToken) {
        return ResponseEntity.status(HttpStatus.OK).body(
                authService.refresh(refreshToken)
        );
    }
}