package com.unitask.dao;

import com.unitask.dto.assessment.AssessmentSubmissionTuple;
import com.unitask.entity.assessment.AssessmentSubmission;
import com.unitask.exception.ServiceAppException;
import com.unitask.repository.AssessmentSubmissionRepository;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AssessmentSubmissionDAO {

    private final AssessmentSubmissionRepository assessmentSubmissionRepository;

    public Page<AssessmentSubmissionTuple> getAssessmentSubmissionListing(Long ownerId, String assignmentName,
                                                                          String groupName, String subjectCode,
                                                                          LocalDateTime beforeDate, LocalDateTime afterDate, Pageable pageable) {
        String assignment = StringUtils.isNotBlank(assignmentName) ? "%" + assignmentName + "%" : null;
        String group = StringUtils.isNotBlank(groupName) ? "%" + groupName + "%" : null;
        String subject = StringUtils.isNotBlank(subjectCode) ? "%" + subjectCode + "%" : null;
        return assessmentSubmissionRepository.getAssessmentSubmissionListing(ownerId, assignment, group, subject, beforeDate, afterDate, pageable);
    }

    public AssessmentSubmission findById(Long id) {
        return assessmentSubmissionRepository.findById(id).orElseThrow(() -> new ServiceAppException(HttpStatus.BAD_REQUEST, "Not found"));
    }

    public AssessmentSubmission save(AssessmentSubmission assessmentSubmission) {
        return assessmentSubmissionRepository.save(assessmentSubmission);
    }

    public Page<AssessmentSubmission> getAllAssessmentSubmissionsBaseOnIndividualAndGroup(List<Long> groupId, Long userId, String docName, String assessmentName,
                                                                                          String subjectName, LocalDateTime beforeSubmissionDate, LocalDateTime afterSubmissionDate
            , Pageable pageable) {
        String documentName = StringUtils.isNotBlank(docName) ? "%" + docName + "%" : null;
        String assName = StringUtils.isNotBlank(assessmentName) ? "%" + assessmentName + "%" : null;
        String subName = StringUtils.isNotBlank(subjectName) ? "%" + subjectName + "%" : null;
        return assessmentSubmissionRepository.findByGroupIdUnionIndividual(groupId, userId, documentName, assName, subName, beforeSubmissionDate, afterSubmissionDate, pageable);
    }

    public Optional<AssessmentSubmission> findByGroupId(Long id) {
        return assessmentSubmissionRepository.findByGroup_Id(id);
    }

    public Optional<AssessmentSubmission> findByStudentAssessment(Long id) {
        return assessmentSubmissionRepository.findByStudentAssessment_Id(id);
    }

    public void delete(AssessmentSubmission assessmentSubmission) {
        assessmentSubmissionRepository.delete(assessmentSubmission);
    }
}
