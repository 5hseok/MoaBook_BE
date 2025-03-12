package com.server.moabook.core.constant;

public class Constant {

    public static final String[] AUTH_WHITE_LIST = {
            "/oauth/**",
            "/api/health",
            "/api/callback",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/api/login/kakao",
            "/h2-console/**",
    };
}
