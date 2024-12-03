package com.unitask.dao;

import com.unitask.entity.StudentSubject;
import com.unitask.repository.StudentSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentSubjectDAO {

    @Autowired
    private StudentSubjectRepository studentSubjectRepository;

    public List<StudentSubject> findAll() {
        return studentSubjectRepository.findAll();
    }

    public StudentSubject save(StudentSubject studentSubject) {
        if (studentSubject == null) {
            return null;
        }
        return studentSubjectRepository.save(studentSubject);
    }
}
