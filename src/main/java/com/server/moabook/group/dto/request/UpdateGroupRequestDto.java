package com.server.moabook.group.dto.request;

import jakarta.validation.constraints.NotNull;

public record UpdateGroupRequestDto(
        @NotNull(message = "그룹의 아이디는 비어있을 수 없습니다.")
        Long groupId,
        @NotNull(message = "그룹의 이름이 비어있을 수 없습니다.")
        String name,
        @NotNull(message = "색상의 이름은 비어있을 수 없습니다.")
        String color
) {
}
