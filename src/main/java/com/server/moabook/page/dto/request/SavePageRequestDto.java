package com.server.moabook.page.dto.request;

import com.server.moabook.page.dto.ElementDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SavePageRequestDto(
        @NotNull(message = "Element 정보는 비어있을 수 없습니다.")
        @Valid
        List<ElementDto> elementDtos
) {
}
