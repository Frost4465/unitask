package com.unitask.mapper;

import com.unitask.dto.assessment.AssessmentRequest;
import com.unitask.dto.assessment.AssessmentResponse;
import com.unitask.entity.assessment.Assessment;
import com.unitask.util.OssUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = OssUtil.class)
public interface AssessmentMapper {
    AssessmentMapper INSTANCE = Mappers.getMapper(AssessmentMapper.class);

    @Mapping(target = "attachedFile", ignore = true)
    AssessmentResponse toResponse(Assessment assessment);

    @Mapping(target = "attachedFile", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subject", ignore = true)
    void update(@MappingTarget Assessment assessment, AssessmentRequest assessmentRequest);

}
