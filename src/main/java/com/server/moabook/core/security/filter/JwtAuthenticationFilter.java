package com.server.moabook.core.security.filter;

import com.server.moabook.core.exception.UnauthorizedException;
import com.server.moabook.core.exception.message.ErrorMessage;
import com.server.moabook.core.constant.Constant;
import com.server.moabook.core.security.jwt.JwtTokenProvider;
import com.server.moabook.core.security.type.JwtValidationType;
import com.server.moabook.core.security.jwt.UserAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;


// 요청에서 Jwt를 검증하는 커스텀 필터 클래스
@Component // 필터를 빈으로 등록
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter  { // 요청이 주어졌을때 한번만 수행되는 필터를 상속받음

    private final JwtTokenProvider jwtTokenProvider;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        // 제외할 URL 리스트 중 하나라도 포함되면 필터 실행 안 함
        return Arrays.stream(Constant.AUTH_WHITE_LIST)
                .anyMatch(pattern -> new AntPathMatcher().match(pattern, path));
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            final String token = getJwtFromRequest(request);
            if (jwtTokenProvider.validateToken(token) == JwtValidationType.VALID_JWT) { // 추출한 토큰의 정보가 VALID_JWT일 경우 사용자 정보 추출
                log.info("token:" + token);
                Long memberId = jwtTokenProvider.getUserFromJwt(token);

                log.info("memberId:" + memberId);
                UserAuthentication authentication = UserAuthentication.createUserAuthentication(memberId);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 추출한 UserId 기반 authentication 객체 생성

                SecurityContextHolder.getContext().setAuthentication(authentication); // SecurityContextHolder에 User 정보 저장
            }
        } catch (Exception exception) {
            throw new UnauthorizedException(ErrorMessage.INVALID_JWT_TOKEN); // 토큰 추출 과정에서 에러 발생 시 UnauthorizedException 발생
        }
        filterChain.doFilter(request, response);
    }

    // Request에서 Token을 추출하는 메서드
    private String getJwtFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization"); // Request의 Authorization 헤더값 추출
        if (StringUtils.hasText(token)) {
            return token; // Bearer 없이 AccessToken을 반환
        }
        return null;
    }
}