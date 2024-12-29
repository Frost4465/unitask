package com.unitask.service.impl;

import com.unitask.dao.AssessmentSubmissionDAO;
import com.unitask.dao.StudentAssessmentDao;
import com.unitask.dto.AssessmentSubmissionResponse;
import com.unitask.dto.PageRequest;
import com.unitask.dto.assessment.AssessmentTuple;
import com.unitask.entity.StudentAssessment;
import com.unitask.entity.assessment.AssessmentSubmission;
import com.unitask.exception.ServiceAppException;
import com.unitask.mapper.StudentAssessmentMapper;
import com.unitask.service.StudentAssessmentService;
import com.unitask.util.PageUtil;
import com.unitask.util.PageWrapperVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class StudentAssessmentServiceImpl implements StudentAssessmentService {

    @Autowired
    private final StudentAssessmentDao studentAssessmentDao;
    @Autowired
    private final AssessmentSubmissionDAO assessmentSubmissionDAO;

    @Override
    public PageWrapperVO getAssessmentListing(PageRequest pageRequest) {
        Pageable pageable = PageUtil.pageable(pageRequest);
        Page<AssessmentTuple> studentAssessmentTuplePage = studentAssessmentDao.getAssessmentListing(pageRequest.getSearch(), pageable);
        return new PageWrapperVO(studentAssessmentTuplePage, studentAssessmentTuplePage.getContent());
    }

    @Override
    public void submit(Long id, MultipartFile file) {
        StudentAssessment studentAssessment = studentAssessmentDao.findById(id);
        if (studentAssessment == null) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Student Assessment Not Found");
        }
        AssessmentSubmission assessmentSubmission = new AssessmentSubmission();
        assessmentSubmission.setAssessment(studentAssessment.getAssessment());
        assessmentSubmission.setStudentAssessment(studentAssessment);
        assessmentSubmission.setSubmissionDate(LocalDateTime.now());
        if (studentAssessment.getGroup() != null) {
            assessmentSubmission.setGroup(studentAssessment.getGroup());
        }
        studentAssessment.setSubmissionDate(LocalDate.now());
        assessmentSubmissionDAO.save(assessmentSubmission);
        studentAssessmentDao.save(studentAssessment);
    }

    @Override
    public AssessmentSubmissionResponse getAssessment(Long id) {
        AssessmentSubmissionResponse assessmentSubmissionResponse = new AssessmentSubmissionResponse();
        StudentAssessment studentAssessment = studentAssessmentDao.findById(id);
        if (studentAssessment == null) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Student Assessment Not Found");
        }
        assessmentSubmissionResponse.setStudentAssessmentResponse(StudentAssessmentMapper.INSTANCE.getResponse(studentAssessment));
        AssessmentSubmission assessmentSubmission = assessmentSubmissionDAO.findLatestByAssessment(studentAssessment.getAssessment().getId());
        if (assessmentSubmission != null) {
            assessmentSubmissionResponse.setFilePath(assessmentSubmission.getPath());
            assessmentSubmissionResponse.setFileName(assessmentSubmission.getName());
            assessmentSubmissionResponse.setUuid(assessmentSubmission.getUuid());
        }
        return assessmentSubmissionResponse;
    }
}
