package com.unitask.service;

import com.unitask.dto.studentAssessment.StudentAssessmentTuple;
import com.unitask.dto.studentSubject.StudentSubjectTuple;
import com.unitask.util.PageWrapperVO;

import java.util.List;

public interface StudentSubjectService {

    List<StudentSubjectTuple> getListing();

    String enroll(Long subjectId);

    PageWrapperVO getAssessmentListing(String search);
}
