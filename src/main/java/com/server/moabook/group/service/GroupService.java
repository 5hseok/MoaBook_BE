package com.server.moabook.group.service;

import com.server.moabook.global.exception.NotFoundException;
import com.server.moabook.global.exception.message.ErrorMessage;
import com.server.moabook.group.domain.Group;
import com.server.moabook.group.dto.GroupMapper;
import com.server.moabook.group.dto.request.CreateGroupRequestDto;
import com.server.moabook.group.dto.request.DeleteGroupRequestDto;
import com.server.moabook.group.dto.request.UpdateGroupRequestDto;
import com.server.moabook.group.dto.response.SelectGroupResponseDto;
import com.server.moabook.group.repository.GroupRepository;
import com.server.moabook.oauth2.entity.SocialUserEntity;
import com.server.moabook.oauth2.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public void createGroup(Long userId, CreateGroupRequestDto createGroupRequestDto){
        SocialUserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(String.valueOf(ErrorMessage.USER_NOT_FOUND)));
        Group group = GroupMapper.toEntity(createGroupRequestDto,user);
        groupRepository.save(group);
    }

    public SelectGroupResponseDto selectGroup(Long userId){
        SocialUserEntity socialUserEntity = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(String.valueOf(ErrorMessage.USER_NOT_FOUND)));
        return GroupMapper.toDTO(socialUserEntity);
    }

    public void updateGroup(Long userId, UpdateGroupRequestDto updateGroupRequestDto){
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(String.valueOf(ErrorMessage.USER_NOT_FOUND)));
        Group group = groupRepository.findById(updateGroupRequestDto.groupId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.GROUP_NOT_FOUND));
        GroupMapper.update(group, updateGroupRequestDto);
        groupRepository.save(group);
    }

    public void deleteGroup(Long userId, DeleteGroupRequestDto deleteGroupRequestDto){
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(String.valueOf(ErrorMessage.USER_NOT_FOUND)));
        Group group = groupRepository.findById(deleteGroupRequestDto.groupId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.GROUP_NOT_FOUND));
        groupRepository.delete(group);
    }

}
