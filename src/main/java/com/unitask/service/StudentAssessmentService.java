package com.unitask.service;

import com.unitask.dto.AssessmentSubmissionResponse;
import com.unitask.dto.PageRequest;
import com.unitask.dto.StudentAssessmentResponse;
import com.unitask.util.PageWrapperVO;
import org.springframework.web.multipart.MultipartFile;


public interface StudentAssessmentService {

    PageWrapperVO getAssessmentListing(PageRequest pageRequest);

    void submit(Long id, MultipartFile file);

    AssessmentSubmissionResponse getAssessment(Long id);
}
