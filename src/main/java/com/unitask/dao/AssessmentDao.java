package com.unitask.dao;

import com.unitask.entity.Assessment;
import com.unitask.repository.AssessmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class AssessmentDao {

    AssessmentRepository assessmentRepository;

    AssessmentDao(AssessmentRepository assessmentRepository) {
        this.assessmentRepository = assessmentRepository;
    }

    public List<Assessment> saveAll(List<Assessment> assessmentList) {
        if (CollectionUtils.isEmpty(assessmentList)) {
            return null;
        }
        return assessmentRepository.saveAll(assessmentList);
    }

    public List<Assessment> findBySubjectId(Long id) {
        if (id == null) {
            return null;
        }
        return assessmentRepository.findBySubject_Id(id);
    }

}
