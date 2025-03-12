package com.server.moabook.page.dto.response;

import lombok.Builder;

@Builder
public record CreatePageResponseDto(
        Long bookId,
        Long pageId,
        Long pageNumber
) {
}
