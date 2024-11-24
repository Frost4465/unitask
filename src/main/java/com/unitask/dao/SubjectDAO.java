package com.unitask.dao;

import com.unitask.entity.Subject;
import com.unitask.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectDAO {

    SubjectRepository subjectRepository;

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

    public List<Subject> findAll(){
        return subjectRepository.findAll();
    }

}
