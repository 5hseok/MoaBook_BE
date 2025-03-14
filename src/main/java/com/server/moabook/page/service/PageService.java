package com.server.moabook.page.service;

import com.server.moabook.book.domain.Book;
import com.server.moabook.book.repository.BookRepository;
import com.server.moabook.core.exception.BusinessException;
import com.server.moabook.core.exception.NotFoundException;
import com.server.moabook.core.exception.message.ErrorMessage;
import com.server.moabook.user.repository.UserRepository;
import com.server.moabook.page.domain.Element;
import com.server.moabook.page.domain.Page;
import com.server.moabook.page.dto.PageMapper;
import com.server.moabook.page.dto.request.SavePageRequestDto;
import com.server.moabook.page.dto.response.CreatePageResponseDto;
import com.server.moabook.page.dto.response.SelectAllPageResponseDto;
import com.server.moabook.page.dto.response.SelectPageResponseDto;
import com.server.moabook.page.repository.ElementRepository;
import com.server.moabook.page.repository.PageRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PageService {

    private final BookRepository bookRepository;
    private final PageRepository pageRepository;
    private final UserRepository userRepository;
    private final ElementRepository elementRepository;

    public CreatePageResponseDto createPage(Long userId, Long bookId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.BOOK_NOT_FOUND));
        // bookId를 기준으로 봐서 마지막 page의 번호를 가져오고 그 다음 번호로 pageNumber를 지정 후 저장
        Long existMaxPageNumber = pageRepository.findLastPageNumberByBook(book)
                .orElseThrow(() -> new BusinessException(ErrorMessage.FAIL_TO_FIND_LAST_PAGE_NUMBER));
        Page page = PageMapper.toSave(book, existMaxPageNumber + 1);
        pageRepository.save(page);

        return CreatePageResponseDto.builder()
                .bookId(book.getId())
                .pageId(page.getPageId())
                .pageNumber(page.getPageNumber())
                .build();
    }

    public void savePage(Long bookId, Long pageNumber, SavePageRequestDto savePageRequestDto) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.BOOK_NOT_FOUND));
        try {
            Page page = pageRepository.findByBookIdAndPageNumber(book.getId(), pageNumber)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.PAGE_NOT_FOUND));

            //저장된 page의 id를 이용하여 element를 저장
            List<Element> elements = savePageRequestDto.elementDtos().stream()
                    .map(element -> PageMapper.toEntity(page, element))
                    .toList();

            elementRepository.saveAll(elements);
        } catch (Exception e) {
            log.info("페이지 저장 실패");
            log.error(e.getMessage());
            throw new IllegalStateException(String.valueOf(ErrorMessage.PAGE_NOT_FOUND));
        }
    }

    public SelectPageResponseDto selectPage(
            Long userId,
            @NotNull(message = "책의 아이디는 비어있을 수 없습니다.")
            Long bookId,
            @NotNull(message = "페이지의 숫자는 비어있을 수 없습니다.")
            Long pageNumber) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(String.valueOf(ErrorMessage.USER_NOT_FOUND)));
        Page page = pageRepository.findByBookIdAndPageNumber(
                bookId,
                pageNumber
        ).orElseThrow(() -> new NotFoundException(ErrorMessage.PAGE_NOT_FOUND));

        return PageMapper.toDTO(page);
    }

    public SelectAllPageResponseDto selectAllPage(Long userId, @NotNull(message = "책의 아이디는 비어있을 수 없습니다.") Long bookId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(String.valueOf(ErrorMessage.USER_NOT_FOUND)));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalStateException(String.valueOf(ErrorMessage.BOOK_NOT_FOUND)));
        return PageMapper.allPageResponseDto(book);
    }

    // 중간 page가 삭제되면 pageNumber를 다시 수정하기
    public void deletePage(Long userId, Long bookId, Long pageNumber) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(String.valueOf(ErrorMessage.USER_NOT_FOUND)));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.BOOK_NOT_FOUND));
        Page page = pageRepository.findByBookIdAndPageNumber(book.getId(), pageNumber)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.PAGE_NOT_FOUND));
        pageRepository.delete(page);
    }

}
