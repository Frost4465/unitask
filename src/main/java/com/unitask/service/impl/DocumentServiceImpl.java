package com.unitask.service.impl;

import com.unitask.dao.AppUserDAO;
import com.unitask.dao.AssessmentSubmissionDAO;
import com.unitask.dao.GroupDao;
import com.unitask.dto.DocumentPageRequest;
import com.unitask.dto.DocumentResponse;
import com.unitask.dto.PageRequest;
import com.unitask.entity.Group;
import com.unitask.entity.User.AppUser;
import com.unitask.entity.assessment.AssessmentSubmission;
import com.unitask.service.ContextService;
import com.unitask.service.DocumentService;
import com.unitask.util.PageUtil;
import com.unitask.util.PageWrapperVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentServiceImpl extends ContextService implements DocumentService {

    @Autowired
    private AssessmentSubmissionDAO assessmentSubmissionDAO;
    @Autowired
    private AppUserDAO appUserDAO;
    @Autowired
    private GroupDao groupDao;

    @Override
    public PageWrapperVO getListing(DocumentPageRequest documentPageRequest) {
        PageRequest pageRequest = new PageRequest(documentPageRequest.getPage(), documentPageRequest.getPageSize(), documentPageRequest.getSearch(), documentPageRequest.getSort());
        Pageable pageable = PageUtil.pageable(pageRequest);
        AppUser appUser = appUserDAO.findByEmail(getCurrentAuthUsername());
        List<Long> groupList = groupDao.findByUserId(appUser.getId()).stream().map(Group::getId).toList();
        Page<AssessmentSubmission> assessmentSubmissionListing = assessmentSubmissionDAO.
                getAllAssessmentSubmissionsBaseOnIndividualAndGroup(groupList, appUser.getId(), documentPageRequest.getAssessmentName(),
                        documentPageRequest.getSubjectName(), documentPageRequest.getBeforeSubmissionDate(),
                        documentPageRequest.getAfterSubmissionDate(), pageable);
        return new PageWrapperVO(assessmentSubmissionListing,
                assessmentSubmissionListing.getContent().stream().map(ass -> {
                    DocumentResponse documentResponse = new DocumentResponse();
                    documentResponse.setId(ass.getId());
                    documentResponse.setName(ass.getName());
                    documentResponse.setAssessmentName(ass.getAssessment().getName());
                    documentResponse.setSubjectName(ass.getAssessment().getSubject().getName());
                    documentResponse.setSubmissionDate(ass.getSubmissionDate());
                    return documentResponse;
                }).toList());
    }
}
