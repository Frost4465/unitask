package com.unitask.service.impl;

import com.unitask.dao.StudentSubjectDAO;
import com.unitask.dao.SubjectDAO;
import com.unitask.dto.studentSubject.StudentSubjectResponse;
import com.unitask.dto.studentSubject.StudentSubjectTuple;
import com.unitask.service.ContextService;
import com.unitask.service.StudentSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentSubjectServiceImpl extends ContextService implements StudentSubjectService {

    @Autowired
    private StudentSubjectDAO studentSubjectDAO;

    @Autowired
    private SubjectDAO subjectDAO;

    public List<StudentSubjectTuple> getListing() {
        List<StudentSubjectTuple> studentSubject = subjectDAO.findByStudentEmail(getCurrentAuthUsername());
        return studentSubject;
    }

    @Override
    public String enroll(Long subjectId) {
        return "OK";
    }
}
