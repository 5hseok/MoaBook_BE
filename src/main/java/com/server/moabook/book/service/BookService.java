package com.server.moabook.book.service;

import com.server.moabook.book.domain.Book;
import com.server.moabook.book.dto.BookMapper;
import com.server.moabook.book.dto.request.CreateBookRequestDto;
import com.server.moabook.book.dto.request.DeleteBookRequestDto;
import com.server.moabook.book.dto.request.UpdateBookRequestDto;
import com.server.moabook.book.dto.response.SelectBookResponseDto;
import com.server.moabook.book.repository.BookRepository;
import com.server.moabook.core.exception.NotFoundException;
import com.server.moabook.core.exception.message.ErrorMessage;
import com.server.moabook.group.domain.Group;
import com.server.moabook.group.repository.GroupRepository;
import com.server.moabook.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public void createBook(Long userId, CreateBookRequestDto createBookRequestDto){
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(String.valueOf(ErrorMessage.USER_NOT_FOUND)));
        Group group = groupRepository.findById(createBookRequestDto.groupId())
                .orElseThrow(() -> new IllegalStateException(String.valueOf(ErrorMessage.GROUP_NOT_FOUND)));
        Book book = BookMapper.toEntity(createBookRequestDto, group);
        bookRepository.save(book);
    }

    public SelectBookResponseDto selectBook(Long userId, @NotNull(message = "그룹이 없을 수 없습니다.") Long groupId){
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(String.valueOf(ErrorMessage.USER_NOT_FOUND)));
        Group group = groupRepository.findById(groupId)
            .orElseThrow(()-> new IllegalStateException(String.valueOf(ErrorMessage.GROUP_NOT_FOUND)));
        return BookMapper.toDTO(group);
    }

    public void updateBook(Long userId, UpdateBookRequestDto updateBookRequestDto){
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(String.valueOf(ErrorMessage.USER_NOT_FOUND)));
        groupRepository.findById(updateBookRequestDto.groupId())
                .orElseThrow(() -> new IllegalStateException(String.valueOf(ErrorMessage.GROUP_NOT_FOUND)));
        Book book = bookRepository.findById(updateBookRequestDto.bookId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.GROUP_NOT_FOUND));
        BookMapper.update(book, updateBookRequestDto);
        bookRepository.save(book);
    }

    public void deleteGroup(Long userId, DeleteBookRequestDto deleteBookRequestDto){
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(String.valueOf(ErrorMessage.USER_NOT_FOUND)));
        groupRepository.findById(deleteBookRequestDto.groupId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.GROUP_NOT_FOUND));
        Book book = bookRepository.findById(deleteBookRequestDto.bookId())
                        .orElseThrow(()-> new NotFoundException(ErrorMessage.BOOK_NOT_FOUND));
        bookRepository.delete(book);
    }

}
