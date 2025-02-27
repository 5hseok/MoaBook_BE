package com.server.moabook.book.controller;

import com.server.moabook.book.dto.request.CreateBookRequestDto;
import com.server.moabook.book.dto.request.DeleteBookRequestDto;
import com.server.moabook.book.dto.request.UpdateBookRequestDto;
import com.server.moabook.book.dto.response.SelectBookResponseDto;
import com.server.moabook.book.service.BookService;
import com.server.moabook.global.exception.dto.SuccessStatusResponse;
import com.server.moabook.global.exception.message.SuccessMessage;
import com.server.moabook.global.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
@Tag(name = "Book", description = "책(Book) 관련 API")
public class BookController {

    private final JwtTokenProvider jwtTokenProvider;
    private final BookService bookService;

    @Operation(summary = "책 생성", description = "새로운 책을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "책 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping
    public ResponseEntity<SuccessStatusResponse<Void>> create(@RequestHeader("Authorization") String token,
                                                                                @Valid @RequestBody CreateBookRequestDto createBookRequestDto){
        Long userId = jwtTokenProvider.getUserFromJwt(token);
        bookService.createBook(userId, createBookRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessStatusResponse.of(
                        SuccessMessage.CREATE_BOOK_SUCCESS, null
                )
        );
    }

    @GetMapping("/{groupId}")
    @Operation(summary = "책 조회", description = "책 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "책 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    public ResponseEntity<SuccessStatusResponse<SelectBookResponseDto>> select(@RequestHeader("Authorization") String token,
                                                                                    @Valid @PathVariable("groupId") Long groupId){

        Long userId = jwtTokenProvider.getUserFromJwt(token);
        SelectBookResponseDto selectBookResponseDto = bookService.selectBook(userId, groupId);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessStatusResponse.of(
                        SuccessMessage.SELECT_BOOK_SUCCESS,selectBookResponseDto
                )
        );
    }

    @Operation(summary = "책 수정", description = "기존 책 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "책 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PutMapping
    public ResponseEntity<SuccessStatusResponse<Void>> update(@RequestHeader("Authorization") String token,
                                                              @Valid @RequestBody UpdateBookRequestDto updateBookRequestDto){
        Long userId = jwtTokenProvider.getUserFromJwt(token);
        bookService.updateBook(userId, updateBookRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessStatusResponse.of(
                        SuccessMessage.UPDATE_BOOK_SUCCESS, null
                )
        );
    };

    @Operation(summary = "책 삭제", description = "책을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "책 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @DeleteMapping
    public ResponseEntity<SuccessStatusResponse<Void>> delete(@RequestHeader("Authorization") String token,
                                                                                @Valid @RequestBody DeleteBookRequestDto deleteBookRequestDto){
        Long userId = jwtTokenProvider.getUserFromJwt(token);
        bookService.deleteGroup(userId, deleteBookRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessStatusResponse.of(
                        SuccessMessage.DELETE_BOOK_SUCCESS, null
                )
        );
    };

}
