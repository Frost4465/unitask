package com.unitask.service;

import com.unitask.dto.subject.SubjectRequest;
import com.unitask.entity.Subject;

import java.util.List;

public interface SubjectService {

    void create(SubjectRequest subjectRequest);

    Subject updateSubject(Long id, SubjectRequest subjectRequest);

    Subject getSubject(Long subjectId);

    List<Subject> getListing();

}
