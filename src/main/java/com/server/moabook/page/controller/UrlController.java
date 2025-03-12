package com.server.moabook.page.controller;

import com.server.moabook.core.exception.dto.SuccessStatusResponse;
import com.server.moabook.core.exception.message.SuccessMessage;
import com.server.moabook.core.security.jwt.JwtTokenProvider;
import com.server.moabook.page.dto.request.UrlCreateRequest;
import com.server.moabook.page.dto.response.UrlResponse;
import com.server.moabook.page.service.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/page/{pageId}/url")
@RequiredArgsConstructor
@Slf4j
public class UrlController {

    private final UrlService urlService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "URL 생성", description = "URL을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "URL 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping
    public ResponseEntity<SuccessStatusResponse<UrlResponse>> createUrlElement(
            @RequestHeader("Authorization") String token,
            @PathVariable Long pageId,
            @RequestBody UrlCreateRequest request
    ) {
        Long userId = jwtTokenProvider.getUserFromJwt(token);
        UrlResponse response = urlService.createUrlElement(userId, pageId, request);
        return ResponseEntity.ok(
                SuccessStatusResponse.of(SuccessMessage.CREATE_URL_SUCCESS, response)
        );
    }

}
