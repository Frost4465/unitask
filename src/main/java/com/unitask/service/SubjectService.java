package com.unitask.service;

import com.unitask.dto.subject.SubjectRequest;
import com.unitask.dto.subject.SubjectResponse;
import com.unitask.entity.Subject;

import java.util.List;

public interface SubjectService {

    void create(SubjectRequest subjectRequest);

    Subject updateSubject(Long id, SubjectRequest subjectRequest);

    SubjectResponse getSubject(Long subjectId);

    List<SubjectResponse> getListing();

}
