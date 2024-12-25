package com.unitask.dao;

import com.unitask.constant.error.SubjectErrorConstant;
import com.unitask.dto.subject.SubjectTuple;
import com.unitask.entity.Subject;
import com.unitask.exception.ServiceAppException;
import com.unitask.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
        return subjectRepository.findById(id).orElseThrow(() -> new ServiceAppException(HttpStatus.BAD_REQUEST, SubjectErrorConstant.NOT_FOUND));
    }

    public Page<SubjectTuple> findListing(Pageable pageable, String email, String search) {
        return subjectRepository.findListing(pageable, email, search);
    }
}
