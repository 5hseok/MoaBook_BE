package com.server.moabook.page.dto;


import com.server.moabook.book.domain.Book;
import com.server.moabook.page.domain.Element;
import com.server.moabook.page.domain.Page;
import com.server.moabook.page.dto.response.SelectAllPageResponseDto;
import com.server.moabook.page.dto.response.SelectPageResponseDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PageMapper {

    public static Element toEntity(Page page, ElementDto elementDto) {
        return Element.builder()
                .page(page)
                .elementType(elementDto.elementType())
                .xPosition(elementDto.xPosition())
                .yPosition(elementDto.yPosition())
                .content(elementDto.content())
                .link(elementDto.link())
                .title(elementDto.title())
                .thumbnailUrl(elementDto.thumbnailUrl())
                .build();
    }

    public static Page toSave(Book book, Long pageNumber) {
        return Page.builder()
                .book(book)
                .pageNumber(pageNumber)
                .build();
    }

    public static SelectPageResponseDto toDTO(Page page) {
        List<ElementDto> elementDtos = page.getElements().stream()
                .map(element -> new ElementDto(
                        element.getElementType(),
                        Optional.ofNullable(element.getTitle()).orElse(null),
                        Optional.ofNullable(element.getLink()).orElse(null),
                        Optional.ofNullable(element.getYPosition()).orElse(null),
                        Optional.ofNullable(element.getXPosition()).orElse(null),
                        Optional.ofNullable(element.getContent()).orElse(null),
                        Optional.ofNullable(element.getThumbnailUrl()).orElse(null)
                ))
                .collect(Collectors.toList());
        return new SelectPageResponseDto(elementDtos);
    }

    public static SelectAllPageResponseDto allPageResponseDto(Book book) {
        List<PageDto> pageDtos = book.getPages().stream().map(page -> {
            List<ElementDto> elementDtos = page.getElements().stream()
                    .map(element ->
                            new ElementDto(
                                    element.getElementType(),
                                    Optional.ofNullable(element.getTitle()).orElse(null),
                                    Optional.ofNullable(element.getLink()).orElse(null),
                                    Optional.ofNullable(element.getYPosition()).orElse(null),
                                    Optional.ofNullable(element.getXPosition()).orElse(null),
                                    Optional.ofNullable(element.getContent()).orElse(null),
                                    Optional.ofNullable(element.getThumbnailUrl()).orElse(null)
                            ))
                    .collect(Collectors.toList());
            return new PageDto(page.getPageId(), page.getPageNumber(), elementDtos);
        }).collect(Collectors.toList());

        return new SelectAllPageResponseDto(pageDtos);
    }

}
