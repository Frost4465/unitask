package com.unitask.service.impl;

import com.unitask.constant.Enum.GeneralStatus;
import com.unitask.dao.AppUserDAO;
import com.unitask.dao.StudentAssessmentDao;
import com.unitask.dao.StudentSubjectDAO;
import com.unitask.dao.SubjectDAO;
import com.unitask.dto.PageRequest;
import com.unitask.dto.studentAssessment.StudentAssessmentTuple;
import com.unitask.dto.studentSubject.StudentSubjectTuple;
import com.unitask.entity.Assessment;
import com.unitask.entity.StudentAssessment;
import com.unitask.entity.StudentSubject;
import com.unitask.entity.Subject;
import com.unitask.entity.User.AppUser;
import com.unitask.exception.ServiceAppException;
import com.unitask.service.ContextService;
import com.unitask.service.StudentSubjectService;
import com.unitask.util.PageUtil;
import com.unitask.util.PageWrapperVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentSubjectServiceImpl extends ContextService implements StudentSubjectService {

    @Autowired
    private StudentSubjectDAO studentSubjectDAO;

    @Autowired
    private SubjectDAO subjectDAO;
    @Autowired
    private AppUserDAO appUserDAO;
    @Autowired
    private StudentAssessmentDao studentAssessmentDao;


    public List<StudentSubjectTuple> getListing() {
        List<StudentSubjectTuple> studentSubject = subjectDAO.findByStudentEmail(getCurrentAuthUsername());
        return studentSubject;
    }

    @Override
    public String enroll(Long subjectId) {
        AppUser appUser = appUserDAO.findByEmail(getCurrentAuthUsername());
        if (appUser == null) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "User not Found");
        }
        Subject subject = subjectDAO.findById(subjectId);
        if (subject == null) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Subject Not Found");
        }
        StudentSubject studentSubject = new StudentSubject();
        studentSubject.setUser(appUser);
        studentSubject.setSubject(subject);
        studentSubject.setEnrollmentDate(LocalDate.now());
        studentSubject.setStatus(GeneralStatus.ACTIVE);
        studentSubjectDAO.save(studentSubject);
        List<Assessment> assessmentList = subject.getAssessment();
        if (!CollectionUtils.isEmpty(assessmentList)) {
            studentAssessmentDao.saveAll(
                    assessmentList.stream().map(ass -> {
                        StudentAssessment assessment = new StudentAssessment();
                        assessment.setUser(appUser);
                        assessment.setAssessment(ass);
                        assessment.setEnrollmentDate(LocalDate.now());
                        assessment.setStatus(GeneralStatus.ACTIVE);
                        return assessment;
                    }).toList());
        }
        return "OK";
    }

    @Override
    public PageWrapperVO getAssessmentListing(String search) {
        Pageable pageable = PageUtil.pageable(new PageRequest());
        Page<StudentAssessmentTuple> studentAssessmentTuplePage = studentAssessmentDao.getAssessmentListing(search, pageable);
        return new PageWrapperVO(studentAssessmentTuplePage, studentAssessmentTuplePage.getContent());

    }
}
