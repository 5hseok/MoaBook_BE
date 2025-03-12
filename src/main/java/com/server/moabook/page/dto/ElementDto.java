package com.server.moabook.page.dto;

import com.server.moabook.page.domain.ElementType;
import jakarta.validation.constraints.NotNull;

public record ElementDto(

        @NotNull(message = "타입은 비어있을 수 없습니다.")
        ElementType elementType,
        @NotNull(message = "X 포지션 값은 비어있을 수 없습니다.")
        String xPosition,
        @NotNull(message = "Y 포지션 값은 비어있을 수 없습니다.")
        String yPosition,
        @NotNull(message = "내용은 비어있을 수 없습니다.")
        String content
) {
}
