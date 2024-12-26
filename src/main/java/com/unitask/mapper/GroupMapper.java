package com.unitask.mapper;

import com.unitask.dto.GroupMemberListDto;
import com.unitask.dto.group.GroupRequest;
import com.unitask.dto.group.GroupResponse;
import com.unitask.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GroupMapper {

    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    @Mapping(target = "id", ignore = true)
    Group toEntity(GroupRequest groupRequest);

    GroupResponse toResponse(Group group);

}

