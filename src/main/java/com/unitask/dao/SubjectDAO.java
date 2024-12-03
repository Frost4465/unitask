package com.unitask.dao;

import com.unitask.dto.studentSubject.StudentSubjectTuple;
import com.unitask.entity.Subject;
import com.unitask.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectDAO {

    @Autowired
    private SubjectRepository subjectRepository;

    SubjectDAO(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Subject save(Subject subject) {
        if (subject == null) {
            return null;
        }
        return subjectRepository.save(subject);
    }

    public Subject findById(Long id) {
        if (id == null) {
            return null;
        }
        return subjectRepository.findById(id).orElse(null);
    }

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public List<StudentSubjectTuple> findByStudentEmail(String email) {
        return subjectRepository.findByStudentEmail(email);
    }
}
