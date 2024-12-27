package com.unitask.dao;

import com.unitask.dto.assessment.AssessmentSubmissionTuple;
import com.unitask.entity.assessment.AssessmentSubmission;
import com.unitask.exception.ServiceAppException;
import com.unitask.repository.AssessmentSubmissionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AssessmentSubmissionDAO {

    private final AssessmentSubmissionRepository assessmentSubmissionRepository;

    public Page<AssessmentSubmissionTuple> getAssessmentSubmissionListing(Long ownerId, Pageable pageable) {
        return assessmentSubmissionRepository.getAssessmentSubmissionListing(ownerId, pageable);
    }

    public AssessmentSubmission findById(Long id) {
        return assessmentSubmissionRepository.findById(id).orElseThrow(()-> new ServiceAppException(HttpStatus.BAD_REQUEST,"Not found"));
    }
}
