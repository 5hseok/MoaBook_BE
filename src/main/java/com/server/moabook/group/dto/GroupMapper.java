package com.server.moabook.group.dto;

import com.server.moabook.group.domain.Group;
import com.server.moabook.group.dto.request.CreateGroupRequestDto;
import com.server.moabook.group.dto.request.UpdateGroupRequestDto;
import com.server.moabook.group.dto.response.SelectGroupResponseDto;
import com.server.moabook.oauth2.entity.SocialUserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class GroupMapper {

    public static Group toEntity(CreateGroupRequestDto createGroupRequestDto, SocialUserEntity user){
        return Group.builder()
            .name(createGroupRequestDto.name())
            .color(createGroupRequestDto.color())
            .user(user)
            .build();
    }

    public static SelectGroupResponseDto toDTO(SocialUserEntity socialUserEntity) {
        List<GroupDto> groupDtos = socialUserEntity.getGroups().stream()
                .map(group -> new GroupDto(group.getGroupId(), group.getName(), group.getColor()))
                .collect(Collectors.toList());
        return new SelectGroupResponseDto(groupDtos);
    }

    public static void update(Group group, UpdateGroupRequestDto updateGroupRequestDto) {
        group.changeName(updateGroupRequestDto.name());
        group.changeColor(updateGroupRequestDto.color());
    }

}
