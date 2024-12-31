package com.unitask.service.impl;

import com.unitask.constant.Enum.UserRole;
import com.unitask.dao.*;
import com.unitask.dto.DocumentPageRequest;
import com.unitask.dto.DocumentResponse;
import com.unitask.dto.PageRequest;
import com.unitask.entity.Group;
import com.unitask.entity.Subject;
import com.unitask.entity.User.AppUser;
import com.unitask.entity.assessment.Assessment;
import com.unitask.entity.assessment.AssessmentSubmission;
import com.unitask.exception.ServiceAppException;
import com.unitask.service.ContextService;
import com.unitask.service.DocumentService;
import com.unitask.util.OssUtil;
import com.unitask.util.PageUtil;
import com.unitask.util.PageWrapperVO;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl extends ContextService implements DocumentService {

    @Autowired
    private AssessmentSubmissionDAO assessmentSubmissionDAO;
    @Autowired
    private AppUserDAO appUserDAO;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private OssUtil ossUtil;
    @Autowired
    private SubjectDAO subjectDAO;
    @Autowired
    private AssessmentDao assessmentDao;


    public Map<Assessment, Optional<AssessmentSubmission>> getLatestSubmissionsByAssessment(List<AssessmentSubmission> submissions) {
        return submissions.stream().collect(Collectors.groupingBy(AssessmentSubmission::getAssessment,
                Collectors.maxBy(Comparator.comparing(AssessmentSubmission::getSubmissionDate))));
    }


    @Override
    public PageWrapperVO getListing(DocumentPageRequest documentPageRequest) {
        PageRequest pageRequest = new PageRequest(documentPageRequest.getPage(), documentPageRequest.getPageSize(), documentPageRequest.getSearch(), documentPageRequest.getSort());
        Pageable pageable = PageUtil.pageable(pageRequest);
        AppUser appUser = appUserDAO.findByEmail(getCurrentAuthUsername());
        if (appUser.getUserRole().equals(UserRole.LECTURER)){
            List<Subject> subjectList = subjectDAO.findByOwnerId(appUser.getId());
            if (CollectionUtils.isEmpty(subjectList)){
                return null;
            }
            List<Long> subjectIds = subjectList.stream().map(Subject::getId).toList();
            List<Assessment> assessmentList = assessmentDao.findBySubjectList(subjectIds);


        }










        List<Long> groupList = groupDao.findByUserId(appUser.getId()).stream().map(Group::getId).toList();
        Page<AssessmentSubmission> assessmentSubmissionListing = assessmentSubmissionDAO.
                getAllAssessmentSubmissionsBaseOnIndividualAndGroup(groupList, appUser.getId(), documentPageRequest.getSearch(), documentPageRequest.getAssessmentName(),
                        documentPageRequest.getSubjectName(), documentPageRequest.getBeforeSubmissionDate(),
                        documentPageRequest.getAfterSubmissionDate(), pageable);
        Map<Assessment, Optional<AssessmentSubmission>> optionalMap = assessmentSubmissionListing.getContent()
                .stream()
                .collect(Collectors.groupingBy(AssessmentSubmission::getAssessment,
                        Collectors.maxBy(Comparator.comparing(AssessmentSubmission::getSubmissionDate))));
        List<DocumentResponse> documentResponseList = new ArrayList<>();
        optionalMap.forEach((assessment, latestSubmission) -> {
            latestSubmission.ifPresent(ass -> {
                DocumentResponse documentResponse = new DocumentResponse();
                documentResponse.setId(ass.getId());
                documentResponse.setName(ass.getName());
                documentResponse.setAssessmentName(ass.getAssessment().getName());
                documentResponse.setSubjectName(ass.getAssessment().getSubject().getName());
                documentResponse.setSubmissionDate(ass.getSubmissionDate());
                documentResponse.setPath(ass.getPath());
                documentResponse.setUuid(ass.getUuid());
                documentResponse.setFileName(ass.getName());
                documentResponseList.add(documentResponse);
            });
        });
        return new PageWrapperVO(assessmentSubmissionListing, documentResponseList);
    }

    @Override
    public void downloadFile(Long id, HttpServletResponse response) {
        AssessmentSubmission assessmentSubmission = assessmentSubmissionDAO.findById(id);
        if (assessmentSubmission == null) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Submission does not exists.");
        }
        if (StringUtils.isBlank(assessmentSubmission.getPath())) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Document does not exists.");
        }
        URL url = ossUtil.getObjectURL(assessmentSubmission.getPath());
        ossUtil.getObjectURL(assessmentSubmission.getPath());
        String fileName = new File(assessmentSubmission.getPath()).getName();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        try (InputStream inputStream = url.openStream(); OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error streaming the file content");
        }
    }
}
