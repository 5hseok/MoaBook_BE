package com.server.moabook.user.controller;

import com.server.moabook.core.exception.dto.SuccessStatusResponse;
import com.server.moabook.core.exception.message.SuccessMessage;
import com.server.moabook.core.security.jwt.JwtTokenProvider;
import com.server.moabook.user.dto.response.SelectUserResponseDto;
import com.server.moabook.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User", description = "사용자(User) 관련 API")
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Operation(summary = "사용자 조회", description = "현재 로그인된 사용자의 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용자 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping
    public ResponseEntity<SuccessStatusResponse<SelectUserResponseDto>> select(
            @RequestHeader("Authorization") String token) {

        Long userId = jwtTokenProvider.getUserFromJwt(token);
        SelectUserResponseDto selectUserResponseDto = userService.select(userId);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessStatusResponse.of(SuccessMessage.SELECT_USER_SUCCESS, selectUserResponseDto)
        );
    }

    @Operation(summary = "사용자 삭제", description = "현재 로그인된 사용자의 계정을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "사용자 삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @DeleteMapping
    public ResponseEntity<SuccessStatusResponse<Void>> delete(
            @RequestHeader("Authorization") String token) {

        Long userId = jwtTokenProvider.getUserFromJwt(token);
        userService.delete(userId);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessStatusResponse.of(SuccessMessage.DELETE_USER_SUCCESS, null)
        );
    }

    @Operation(summary = "모든 사용자 삭제", description = "모든 사용자 계정을 삭제합니다. (관리자 전용)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "모든 사용자 삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "관리자 권한 필요"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @DeleteMapping("/all")
    public ResponseEntity<SuccessStatusResponse<Void>> deleteAll(
            @RequestHeader("Authorization") String token) {

        Long userId = jwtTokenProvider.getUserFromJwt(token);
        userService.deleteAll(userId);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessStatusResponse.of(SuccessMessage.DELETE_ALL_USER_SUCCESS, null)
        );
    }

    @Operation(summary = "이메일 수신 여부 변경", description = "사용자의 이메일 수신 여부를 업데이트합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "이메일 수신 여부 업데이트 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PatchMapping
    public ResponseEntity<SuccessStatusResponse<Void>> updateReceivedEmail(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "user_email", required = false) String email,
            @RequestParam("is_received_email") boolean checkReceivedEmail) {

        Long userId = jwtTokenProvider.getUserFromJwt(token);
        userService.updateReceivedEmail(userId, email, checkReceivedEmail);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessStatusResponse.of(SuccessMessage.UPDATE_RECEIVED_EMAIL_SUCCESS, null)
        );
    }
}