package com.server.moabook.core.security.dto.response;

import lombok.*;

@Builder
public record SuccessLoginResponseDto (
        Long id,
        String name,
        String email,
        String profile_image_url,
        JwtTokenDto jwtToken,
        boolean received_email
){
}