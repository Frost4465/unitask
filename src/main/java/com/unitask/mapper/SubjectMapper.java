package com.unitask.mapper;

import com.unitask.dto.subject.SubjectResponse;
import com.unitask.entity.Subject;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectMapper {

    SubjectMapper INSTANCE = Mappers.getMapper(SubjectMapper.class);

    @Named("toResponse")
    SubjectResponse toResponse(Subject subject);

    @IterableMapping(qualifiedByName = "toResponse")
    List<SubjectResponse> toResponse(List<Subject> subject);

}
