package com.server.moabook.page.controller;

import com.server.moabook.page.dto.request.*;
import com.server.moabook.global.exception.dto.SuccessStatusResponse;
import com.server.moabook.global.exception.message.SuccessMessage;
import com.server.moabook.global.jwt.JwtTokenProvider;
import com.server.moabook.page.dto.response.SelectAllPageResponseDto;
import com.server.moabook.page.dto.response.SelectPageResponseDto;
import com.server.moabook.page.service.PageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/page")
@Tag(name = "Page", description = "페이지(Page) 관련 API")
public class PageController {

    private final JwtTokenProvider jwtTokenProvider;
    private final PageService pageService;

    @Operation(summary = "페이지 생성", description = "새로운 페이지를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "페이지 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping
    public ResponseEntity<SuccessStatusResponse<Void>> create(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody CreatePageRequestDto createPageRequestDto) {

        Long userId = jwtTokenProvider.getUserFromJwt(token);
        pageService.createPage(userId, createPageRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessStatusResponse.of(SuccessMessage.CREATE_PAGE_SUCCESS, null)
        );
    }

    @Operation(summary = "페이지 저장", description = "기존 페이지를 저장합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "페이지 저장 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/save")
    public ResponseEntity<SuccessStatusResponse<Void>> save(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody SavePageRequestDto savePageRequestDto) {

        Long userId = jwtTokenProvider.getUserFromJwt(token);
        pageService.savePage(userId, savePageRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessStatusResponse.of(SuccessMessage.SAVE_PAGE_SUCCESS, null)
        );
    }

    @Operation(summary = "페이지 조회", description = "단일 페이지 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "페이지 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/{bookId}/{pageNumber}")
    public ResponseEntity<SuccessStatusResponse<SelectPageResponseDto>> select(
            @RequestHeader("Authorization") String token,
            @Valid @PathVariable Long bookId, @Valid @PathVariable Long pageNumber) {

        Long userId = jwtTokenProvider.getUserFromJwt(token);
        SelectPageResponseDto selectPageResponseDto = pageService.selectPage(userId, bookId,pageNumber);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessStatusResponse.of(SuccessMessage.SELECT_PAGE_SUCCESS, selectPageResponseDto)
        );
    }

    @Operation(summary = "모든 페이지 조회", description = "사용자의 모든 페이지를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "모든 페이지 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/all/{bookId}")
    public ResponseEntity<SuccessStatusResponse<SelectAllPageResponseDto>> selectAll(
            @RequestHeader("Authorization") String token,
            @Valid @PathVariable Long bookId) {

        Long userId = jwtTokenProvider.getUserFromJwt(token);
        SelectAllPageResponseDto selectAllPageResponseDto = pageService.selectAllPage(userId, bookId);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessStatusResponse.of(SuccessMessage.SELECT_PAGE_SUCCESS, selectAllPageResponseDto)
        );
    }

    @Operation(summary = "페이지 삭제", description = "페이지를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "페이지 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @DeleteMapping
    public ResponseEntity<SuccessStatusResponse<Void>> delete(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody DeletePageRequestDto deletePageRequestDto) {

        Long userId = jwtTokenProvider.getUserFromJwt(token);
        pageService.deletePage(userId, deletePageRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessStatusResponse.of(SuccessMessage.DELETE_PAGE_SUCCESS, null)
        );
    }

}