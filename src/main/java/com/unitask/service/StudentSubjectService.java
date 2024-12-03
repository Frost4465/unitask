package com.unitask.service;

import com.unitask.dto.studentSubject.StudentSubjectResponse;
import com.unitask.dto.studentSubject.StudentSubjectTuple;

import java.util.List;

public interface StudentSubjectService {

    List<StudentSubjectTuple> getListing();

    String enroll(Long subjectId);
}
