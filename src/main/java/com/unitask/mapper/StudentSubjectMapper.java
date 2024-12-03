package com.unitask.mapper;

import com.unitask.dto.studentSubject.StudentSubjectResponse;
import com.unitask.entity.StudentSubject;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StudentSubjectMapper {

    StudentSubjectMapper INSTANCE = Mappers.getMapper(StudentSubjectMapper.class);

    @Named("toResponse")
    StudentSubjectResponse toResponse(StudentSubject studentSubject);

    @IterableMapping(qualifiedByName = "toResponse")
    List<StudentSubjectResponse> toResponse(List<StudentSubject> studentSubject);

}
