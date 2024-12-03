package com.unitask.service;

import com.unitask.dto.studentSubject.StudentSubjectResponse;

import java.util.List;

public interface StudentSubjectService {

    List<StudentSubjectResponse> getListing();

    String enroll(Long subjectId);
}
