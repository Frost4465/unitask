package com.unitask.service.impl;

import com.unitask.dao.StudentAssessmentDao;
import com.unitask.dto.PageRequest;
import com.unitask.dto.assessment.AssessmentTuple;
import com.unitask.service.StudentAssessmentService;
import com.unitask.util.PageUtil;
import com.unitask.util.PageWrapperVO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentAssessmentServiceImpl implements StudentAssessmentService {

    private final StudentAssessmentDao studentAssessmentDao;

    @Override
    public PageWrapperVO getAssessmentListing(PageRequest pageRequest) {
        Pageable pageable = PageUtil.pageable(pageRequest);
        Page<AssessmentTuple> studentAssessmentTuplePage = studentAssessmentDao.getAssessmentListing(pageRequest.getSearch(), pageable);
        return new PageWrapperVO(studentAssessmentTuplePage, studentAssessmentTuplePage.getContent());
    }

    @Override
    public void submit() {

    }
}
