package com.server.moabook.page.service;

import com.server.moabook.book.domain.Book;
import com.server.moabook.page.dto.request.*;
import com.server.moabook.book.repository.BookRepository;
import com.server.moabook.global.exception.NotFoundException;
import com.server.moabook.global.exception.message.ErrorMessage;
import com.server.moabook.oauth2.repository.UserRepository;
import com.server.moabook.page.domain.Element;
import com.server.moabook.page.domain.Page;
import com.server.moabook.page.dto.PageMapper;
import com.server.moabook.page.dto.response.SelectAllPageResponseDto;
import com.server.moabook.page.dto.response.SelectPageResponseDto;
import com.server.moabook.page.repository.PageRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PageService {

    private final BookRepository bookRepository;
    private final PageRepository pageRepository;
    private final UserRepository userRepository;

    public void createPage(Long userId, CreatePageRequestDto createPageRequestDto){
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(String.valueOf(ErrorMessage.USER_NOT_FOUND)));
        Book book = bookRepository.findById(createPageRequestDto.bookId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.GROUP_NOT_FOUND));
        Page page = PageMapper.toEntity(book);
        pageRepository.save(page);
    }

    public void savePage(Long userId, SavePageRequestDto savePageRequestDto){
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(String.valueOf(ErrorMessage.USER_NOT_FOUND)));
        Book book = bookRepository.findById(savePageRequestDto.bookId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.GROUP_NOT_FOUND));
        Page page = PageMapper.toSave(book, savePageRequestDto);
        pageRepository.save(page);
    }

    public SelectPageResponseDto selectPage(
            Long userId,
            @NotNull(message = "책의 아이디는 비어있을 수 없습니다.")
            Long bookId,
            @NotNull(message = "페이지의 숫자는 비어있을 수 없습니다.")
            Long pageNumber){
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(String.valueOf(ErrorMessage.USER_NOT_FOUND)));
        Page page = pageRepository.findByBook_IdAndPageNumber(
                bookId,
                pageNumber
        );
        if(page.getClass() == null){
            throw new NotFoundException(ErrorMessage.PAGE_NOT_FOUND);
        }
        return PageMapper.toDTO(page);
    }

    public SelectAllPageResponseDto selectAllPage(Long userId, @NotNull(message = "책의 아이디는 비어있을 수 없습니다.") Long bookId){
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(String.valueOf(ErrorMessage.USER_NOT_FOUND)));
        Book book = bookRepository.findById(bookId)
            .orElseThrow(()-> new IllegalStateException(String.valueOf(ErrorMessage.BOOK_NOT_FOUND)));
        return PageMapper.AlltoDTO(book);
    }

    public void deletePage(Long userId, DeletePageRequestDto deletePageRequestDto){
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(String.valueOf(ErrorMessage.USER_NOT_FOUND)));
        bookRepository.findById(deletePageRequestDto.bookId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.GROUP_NOT_FOUND));
        Page page = pageRepository.findById(deletePageRequestDto.pageId())
                .orElseThrow(()-> new NotFoundException(ErrorMessage.PAGE_NOT_FOUND));
        pageRepository.delete(page);
    }

}
