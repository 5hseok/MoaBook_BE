package com.server.moabook.page.dto;

import com.server.moabook.page.domain.ElementType;
import jakarta.validation.constraints.NotNull;

public record ElementDto(

        @NotNull(message = "타입은 비어있을 수 없습니다.")
        ElementType elementType,
        String xPosition,
        String yPosition,
        String content,
        String link,
        String title,
        String thumbnailUrl
) {
}
