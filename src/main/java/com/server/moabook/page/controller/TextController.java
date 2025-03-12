package com.server.moabook.page.controller;

import com.server.moabook.core.exception.dto.SuccessStatusResponse;
import com.server.moabook.core.exception.message.SuccessMessage;
import com.server.moabook.core.security.jwt.JwtTokenProvider;
import com.server.moabook.page.dto.request.TextCreateRequest;
import com.server.moabook.page.dto.request.TextUpdateRequest;
import com.server.moabook.page.dto.response.TextResponse;
import com.server.moabook.page.service.TextService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/page/{pageId}/text")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Text", description = "페이지 내 텍스트 관리 API")
public class TextController {

    private final TextService textService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    @Operation(summary = "텍스트 생성", description = "특정 페이지에 새로운 텍스트를 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "텍스트 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<SuccessStatusResponse<TextResponse>> createText(
            @RequestHeader("Authorization") String token,
            @PathVariable Long pageId,
            @RequestBody TextCreateRequest request) {

        Long userId = jwtTokenProvider.getUserFromJwt(token);
        TextResponse response = textService.createText(userId, pageId, request);

        return ResponseEntity.ok(
                SuccessStatusResponse.of(SuccessMessage.CREATE_TEXT_SUCCESS, response)
        );
    }

    @GetMapping("/{elementId}")
    @Operation(summary = "텍스트 조회", description = "특정 페이지의 텍스트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "텍스트 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "텍스트를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<SuccessStatusResponse<TextResponse>> getText(
            @RequestHeader("Authorization") String token,
            @PathVariable Long pageId,
            @PathVariable Long elementId) {

        Long userId = jwtTokenProvider.getUserFromJwt(token);
        TextResponse response = textService.getText(userId, elementId);

        return ResponseEntity.ok(
                SuccessStatusResponse.of(SuccessMessage.SELECT_TEXT_SUCCESS, response)
        );
    }

    @PutMapping("/{elementId}")
    @Operation(summary = "텍스트 수정", description = "기존 텍스트 내용을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "텍스트 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "텍스트를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<SuccessStatusResponse<TextResponse>> updateText(
            @RequestHeader("Authorization") String token,
            @PathVariable Long pageId,
            @PathVariable Long elementId,
            @RequestBody TextUpdateRequest request) {

        Long userId = jwtTokenProvider.getUserFromJwt(token);
        TextResponse response = textService.updateText(userId, elementId, request);

        return ResponseEntity.ok(
                SuccessStatusResponse.of(SuccessMessage.UPDATE_TEXT_SUCCESS, response)
        );
    }

    @DeleteMapping("/{elementId}")
    @Operation(summary = "텍스트 삭제", description = "페이지 내의 텍스트를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "텍스트 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "텍스트를 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<SuccessStatusResponse<Void>> deleteText(
            @RequestHeader("Authorization") String token,
            @PathVariable Long pageId,
            @PathVariable Long elementId) {

        Long userId = jwtTokenProvider.getUserFromJwt(token);
        textService.deleteText(userId, elementId);

        return ResponseEntity.ok(
                SuccessStatusResponse.of(SuccessMessage.DELETE_TEXT_SUCCESS, null)
        );
    }

    @GetMapping
    @Operation(summary = "모든 텍스트 조회", description = "특정 페이지에 존재하는 모든 텍스트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "모든 텍스트 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    public ResponseEntity<SuccessStatusResponse<List<TextResponse>>> getAllTexts(
            @RequestHeader("Authorization") String token,
            @PathVariable Long pageId) {

        Long userId = jwtTokenProvider.getUserFromJwt(token);
        List<TextResponse> responseList = textService.getAllTextsInPage(userId, pageId);

        return ResponseEntity.ok(
                SuccessStatusResponse.of(SuccessMessage.SELECT_TEXT_SUCCESS, responseList)
        );
    }

}