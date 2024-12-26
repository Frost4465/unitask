package com.unitask.service;

import com.unitask.dto.PageRequest;
import com.unitask.dto.assessment.AssessmentSubmissionTuple;
import org.springframework.data.domain.Page;

public interface AssessmentSubmissionService {

    Page<AssessmentSubmissionTuple> getListing(PageRequest pageRequest);

    String resubmit(Long id);

    void grade(Long id, String grade);
}
