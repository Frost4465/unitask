package com.unitask.service.impl;

import com.unitask.constant.Enum.GeneralStatus;
import com.unitask.dao.AppUserDAO;
import com.unitask.dao.AssessmentSubmissionDAO;
import com.unitask.dao.GroupDao;
import com.unitask.dto.PageRequest;
import com.unitask.dto.assessment.AssessmentSubmissionTuple;
import com.unitask.entity.User.AppUser;
import com.unitask.service.AssessmentSubmissionService;
import com.unitask.service.ContextService;
import com.unitask.util.PageUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AssessmentSubmissionServiceImpl extends ContextService implements AssessmentSubmissionService {

    private final AssessmentSubmissionDAO assessmentSubmissionDAO;
    private final AppUserDAO appUserDAO;

    @Override
    public Page<AssessmentSubmissionTuple> getListing(PageRequest pageRequest) {
        AppUser appUser = appUserDAO.findByEmail(getCurrentAuthUsername());
        Pageable pageable = PageUtil.pageable(pageRequest);
        return assessmentSubmissionDAO.getAssessmentSubmissionListing(appUser.getId(),  pageable);
    }

    @Override
    public String resubmit(Long id) {
        //set the student assessment to be able to submit again
        return null;
    }

    @Override
    public void grade(Long id, String grade) {

    }
}
