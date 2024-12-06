package com.unitask.mapper;

import com.unitask.dto.assessment.AssessmentRequest;
import com.unitask.dto.assessment.AssessmentResponse;
import com.unitask.entity.assessment.Assessment;
import com.unitask.entity.assessment.AssessmentMarkingRubric;
import com.unitask.util.OssUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(uses = OssUtil.class)
public interface AssessmentMapper {
    AssessmentMapper INSTANCE = Mappers.getMapper(AssessmentMapper.class);

    @Mapping(target = "attachedFile", ignore = true)
    @Mapping(target = "subject.assessment", ignore = true)
    AssessmentResponse toResponse(Assessment assessment);

    @BeanMapping(qualifiedByName = "toRubrics")
    @Mapping(target = "studentAssessments", ignore = true)
    @Mapping(target = "generalStatus", ignore = true)
    @Mapping(target = "attachedFile", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subject", ignore = true)
    void update(@MappingTarget Assessment assessment, AssessmentRequest assessmentRequest);

    @Named("toRubrics")
    @AfterMapping()
    default void update(@MappingTarget Assessment assessment) {
        if (CollectionUtils.isNotEmpty(assessment.getAssessmentMarkingRubrics())) {
            for (AssessmentMarkingRubric assessmentMarkingRubric : assessment.getAssessmentMarkingRubrics()) {
                assessmentMarkingRubric.setAssessment(assessment);
            }
        }
    }
}
