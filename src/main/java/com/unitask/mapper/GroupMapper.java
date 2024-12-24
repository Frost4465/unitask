package com.unitask.mapper;

import com.unitask.dto.GroupMemberListDto;
import com.unitask.dto.group.GroupRequest;
import com.unitask.dto.group.GroupResponse;
import com.unitask.entity.Group;
import com.unitask.entity.GroupMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GroupMapper {

    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    @Mapping(target = "groupMembers", ignore = true)
    @Mapping(target = "Id", ignore = true)
    Group toEntity(GroupRequest groupRequest);

    @Mapping(target = "groupMemberList", source = "group.groupMembers")
    GroupResponse toResponse(Group group);

    @Mapping(target = "id", source = "appUser.id")
    @Mapping(target = "name", source = "appUser.name")
    GroupMemberListDto toGroupMemberListDto(GroupMember groupMember);
}

