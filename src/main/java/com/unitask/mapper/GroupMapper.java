package com.unitask.mapper;

import com.unitask.dto.StudentAssessmentResponse;
import com.unitask.dto.group.GroupResponse;
import com.unitask.entity.Group;
import com.unitask.entity.StudentAssessment;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GroupMapper {

    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    @Mapping(target = "studentAssessment", source = "studentAssessment")
    GroupResponse toResponse(Group group);

    @Mapping(target = "name", source = "user.name")
    StudentAssessmentResponse toResponse(StudentAssessment studentAssessment);

    @IterableMapping(elementTargetType = GroupResponse.class)
    List<GroupResponse> toResponseList(List<Group> groups);

}

