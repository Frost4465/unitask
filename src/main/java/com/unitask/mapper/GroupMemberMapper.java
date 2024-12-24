package com.unitask.mapper;

import com.unitask.entity.Group;
import com.unitask.entity.GroupMember;
import com.unitask.entity.User.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GroupMemberMapper {

    GroupMemberMapper INSTANCE = Mappers.getMapper(GroupMemberMapper.class);


    @Mapping(target = "appUser", source = "appUser")
    @Mapping(target = "group", source = "group")
    @Mapping(target = "id" , ignore = true)
    GroupMember toEntity(AppUser appUser, Group group);

}
