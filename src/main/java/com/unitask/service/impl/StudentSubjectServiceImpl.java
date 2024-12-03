package com.unitask.service.impl;

import com.unitask.dao.StudentSubjectDAO;
import com.unitask.dao.SubjectDAO;
import com.unitask.dto.studentSubject.StudentSubjectResponse;
import com.unitask.entity.StudentSubject;
import com.unitask.mapper.StudentSubjectMapper;
import com.unitask.service.StudentSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentSubjectServiceImpl implements StudentSubjectService {

    @Autowired
    private StudentSubjectDAO studentSubjectDAO;

    public List<StudentSubjectResponse> getListing() {
        List<StudentSubject> studentSubject = studentSubjectDAO.findAll();
        return StudentSubjectMapper.INSTANCE.toResponse(studentSubject);
    }

    @Override
    public String enroll(Long subjectId) {
        return "OK";
    }
}
