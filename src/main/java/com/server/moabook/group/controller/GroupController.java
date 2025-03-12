package com.server.moabook.group.controller;

import com.server.moabook.core.exception.dto.SuccessStatusResponse;
import com.server.moabook.core.exception.message.SuccessMessage;
import com.server.moabook.core.security.jwt.JwtTokenProvider;
import com.server.moabook.group.dto.request.CreateGroupRequestDto;
import com.server.moabook.group.dto.request.DeleteGroupRequestDto;
import com.server.moabook.group.dto.request.UpdateGroupRequestDto;
import com.server.moabook.group.dto.response.SelectGroupResponseDto;
import com.server.moabook.group.service.GroupService;
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
@RequestMapping("/group")
@Tag(name = "Group", description = "그룹(Group) 관련 API")
public class GroupController {

    private final JwtTokenProvider jwtTokenProvider;
    private final GroupService groupService;

    @Operation(summary = "그룹 생성", description = "새로운 그룹을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "그룹 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping
    public ResponseEntity<SuccessStatusResponse<Void>> create(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody CreateGroupRequestDto createGroupRequestDto) {

        Long userId = jwtTokenProvider.getUserFromJwt(token);
        groupService.createGroup(userId, createGroupRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessStatusResponse.of(SuccessMessage.CREATE_GROUP_SUCCESS, null)
        );
    }

    @Operation(summary = "그룹 조회", description = "현재 사용자의 그룹 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "그룹 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping
    public ResponseEntity<SuccessStatusResponse<SelectGroupResponseDto>> select(
            @RequestHeader("Authorization") String token) {

        Long userId = jwtTokenProvider.getUserFromJwt(token);
        SelectGroupResponseDto selectGroupResponseDto = groupService.selectGroup(userId);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessStatusResponse.of(SuccessMessage.SELECT_GROUP_SUCCESS, selectGroupResponseDto)
        );
    }

    @Operation(summary = "그룹 수정", description = "기존 그룹 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "그룹 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PutMapping
    public ResponseEntity<SuccessStatusResponse<Void>> update(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody UpdateGroupRequestDto updateGroupRequestDto) {

        Long userId = jwtTokenProvider.getUserFromJwt(token);
        groupService.updateGroup(userId, updateGroupRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessStatusResponse.of(SuccessMessage.UPDATE_GROUP_SUCCESS, null)
        );
    }

    @Operation(summary = "그룹 삭제", description = "그룹을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "그룹 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @DeleteMapping
    public ResponseEntity<SuccessStatusResponse<Void>> delete(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody DeleteGroupRequestDto deleteGroupRequestDto) {

        Long userId = jwtTokenProvider.getUserFromJwt(token);
        groupService.deleteGroup(userId, deleteGroupRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessStatusResponse.of(SuccessMessage.DELETE_GROUP_SUCCESS, null)
        );
    }
}