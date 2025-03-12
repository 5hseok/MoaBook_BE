package com.server.moabook.security.service;

import com.server.moabook.global.exception.BusinessException;
import com.server.moabook.global.exception.NotFoundException;
import com.server.moabook.global.exception.dto.oauth.KakaoUserInfoRequestDto;
import com.server.moabook.global.exception.message.ErrorMessage;
import com.server.moabook.global.jwt.JwtTokenProvider;
import com.server.moabook.oauth2.entity.SocialUserEntity;
import com.server.moabook.oauth2.repository.UserRepository;
import com.server.moabook.security.dto.response.JwtTokenDto;
import com.server.moabook.security.dto.response.KakaoUserInfoDto;
import com.server.moabook.security.dto.response.SuccessLoginResponseDto;
import com.server.moabook.utils.OAuth2Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class GeneralMemberService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2Util oAuth2Util;

    // 카카오 소셜 로그인
    public SuccessLoginResponseDto kakaoAuthSocialLogin(KakaoUserInfoRequestDto kakaoUserInfoRequestDto) {
        String accessToken = refineToken(kakaoUserInfoRequestDto.accessToken());

        KakaoUserInfoDto kakaoUserInfoDto = oAuth2Util.getKakaoUserInfo(accessToken);;

        return processKakaoUserLogin(kakaoUserInfoDto);
    }

    public static String refineToken(String accessToken) {
        return accessToken.startsWith("Bearer ")
                ? accessToken.substring(7)
                : accessToken;
    }

    // 카카오 로그인 프로세스
    private SuccessLoginResponseDto processKakaoUserLogin(KakaoUserInfoDto kakaoUserInfo) {
        SocialUserEntity user = userRepository.findByKakaoUserId(kakaoUserInfo.id());

        if (user == null) {
            // 새로운 사용자 등록
            SocialUserEntity newUser = registerNewKakaoUser(kakaoUserInfo);
            return handleExistingUserLogin(newUser);
        }
        return handleExistingUserLogin(user);
    }

    private SocialUserEntity registerNewKakaoUser(KakaoUserInfoDto kakaoUserInfo) {
        SocialUserEntity newUser =
                new SocialUserEntity().builder()
                        .id(kakaoUserInfo.id())
                        .email(kakaoUserInfo.email())
                        .username(kakaoUserInfo.nickname())
                        .profile_image_url(kakaoUserInfo.profileImageUrl())
                        .role("USER")
                        .updated_at(LocalDateTime.now())
                        .received_email(false)
                        .sended_email(false)
                        .build();
        return userRepository.save(newUser);
    }

    // 기존 사용자 로그인 처리
    private SuccessLoginResponseDto handleExistingUserLogin(SocialUserEntity user) {

        // 토큰 생성 후 업데이트
        JwtTokenDto jwtTokenDto = jwtTokenProvider.generateToken(user.getId());
        user.updateRefreshToken(jwtTokenDto.refreshToken());

        //로그인 시 사용자 사용시간 업데이트
        user.updateUserUpdateTime();
        //로그인 시 사용자가 나중에 다시 접속이 오래 이어지지 않았을 때 다시 이메일을 받을 수 있게 설정
        user.updateSendedEmailFalse();
        // 로그인 후 필요한 데이터를 생성하고 반환
        return SuccessLoginResponseDto.builder()
                .id(user.getId())
                .name(user.getUsername())
                .email(user.getEmail())
                .profile_image_url(user.getProfile_image_url())
                .jwtToken(jwtTokenDto)
                .received_email(user.isReceived_email())
                .build();
    }

    public SuccessLoginResponseDto refresh(String token) {
        String refreshToken = this.refineToken(token);

        Long userId = jwtTokenProvider.getUserFromJwt(refreshToken);

        SocialUserEntity user = userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException(ErrorMessage.USER_NOT_FOUND));

        if (!user.getRefreshToken().equals(refreshToken)) {
            throw new BusinessException(ErrorMessage.INVALID_JWT_TOKEN);
        }

        JwtTokenDto jwtTokenDto = jwtTokenProvider.generateToken(userId);
        // 액세스 토큰을 다시 발급받을 때 refreshToken도 같이 업데이트 하여 장기간 접속하지 않는 한 로그인 유지
        user.updateRefreshToken(jwtTokenDto.refreshToken());

        return SuccessLoginResponseDto.builder()
                .id(user.getId())
                .name(user.getUsername())
                .email(user.getEmail())
                .profile_image_url(user.getProfile_image_url())
                .jwtToken(jwtTokenDto)
                .received_email(user.isReceived_email())
                .build();
    }

}