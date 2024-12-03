package com.unitask.service.impl;

import com.unitask.dao.SubjectDAO;
import com.unitask.dto.PageRequest;
import com.unitask.dto.subject.SubjectRequest;
import com.unitask.dto.subject.SubjectResponse;
import com.unitask.dto.subject.SubjectTuple;
import com.unitask.entity.Subject;
import com.unitask.exception.ServiceAppException;
import com.unitask.mapper.SubjectMapper;
import com.unitask.service.AssessmentService;
import com.unitask.service.ContextService;
import com.unitask.service.SubjectService;
import com.unitask.util.PageUtil;
import com.unitask.util.PageWrapperVO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubjectServiceImpl extends ContextService implements SubjectService {

    private final AssessmentService assessmentService;
    private final SubjectDAO subjectDAO;

    @Override
    public void create(SubjectRequest subjectRequest) {
        Subject subject = subjectDAO.save(Subject.builder()
                .code(subjectRequest.getCode())
                .name(subjectRequest.getName())
                .course(subjectRequest.getCourse())
                .creditHour(subjectRequest.getCreditHour())
                .description(subjectRequest.getDescription())
                .learningOutcome(subjectRequest.getLearningOutcome())
                .lecturerName(subjectRequest.getLecturerName())
                .lecturerContact(subjectRequest.getLecturerContact())
                .lecturerEmail(subjectRequest.getLecturerEmail())
                .lecturerOffice(subjectRequest.getLecturerOffice())
                .status(subjectRequest.getStatus()).build());
        assessmentService.update(subject, subjectRequest.getAssessment());
    }

    @Override
    public void updateSubject(Long id, SubjectRequest subjectRequest) {
        Subject subject = subjectDAO.findById(id);
        if (subject == null) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Subject not Found");
        }
        subject.setCode(subjectRequest.getCode());
        subject.setName(subjectRequest.getName());
        subject.setCourse(subjectRequest.getCourse());
        subject.setCreditHour(subjectRequest.getCreditHour());
        subject.setDescription(subjectRequest.getDescription());
        subject.setLearningOutcome(subjectRequest.getLearningOutcome());
        subject.setLecturerName(subjectRequest.getLecturerName());
        subject.setLecturerContact(subjectRequest.getLecturerContact());
        subject.setLecturerEmail(subjectRequest.getLecturerEmail());
        subject.setLecturerOffice(subjectRequest.getLecturerOffice());
        subject.setStatus(subjectRequest.getStatus());

        subjectDAO.save(subject);
        assessmentService.update(subject, subjectRequest.getAssessment());
    }

    @Override
    public SubjectResponse getSubject(Long subjectId) {
        Subject subject = subjectDAO.findById(subjectId);
        if (subject == null) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Subject not Found");
        }
        return SubjectMapper.INSTANCE.toResponse(subject);
    }

    @Override
    public PageWrapperVO<SubjectTuple> getListing(PageRequest pageRequest) {
        Pageable pageable = PageUtil.pageable(pageRequest);
        Page<SubjectTuple> subjectList = subjectDAO.findListing(pageable, pageRequest.getSearch());
        return new PageWrapperVO<SubjectTuple>(subjectList, subjectList.getContent());
    }

}
