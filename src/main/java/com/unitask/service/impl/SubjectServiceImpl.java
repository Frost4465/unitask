package com.unitask.service.impl;

import com.unitask.constant.Enum.GeneralStatus;
import com.unitask.dao.AppUserDAO;
import com.unitask.dao.AssessmentDao;
import com.unitask.dao.SubjectDAO;
import com.unitask.dto.subject.AssessmentDto;
import com.unitask.dto.subject.SubjectRequest;
import com.unitask.dto.subject.SubjectResponse;
import com.unitask.entity.Assessment;
import com.unitask.entity.Subject;
import com.unitask.exception.ServiceAppException;
import com.unitask.service.SubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final AssessmentDao assessmentDao;
    SubjectDAO subjectDAO;
    AppUserDAO appUserDAO;


    SubjectServiceImpl(SubjectDAO subjectDAO, AppUserDAO appUserDAO, AssessmentDao assessmentDao) {
        this.subjectDAO = subjectDAO;
        this.appUserDAO = appUserDAO;
        this.assessmentDao = assessmentDao;
    }

    @Override
    public void create(SubjectRequest subjectRequest) {
        Subject subject = subjectDAO.save(Subject.builder()
                .code(subjectRequest.getSubjectCode())
                .name(subjectRequest.getSubjectName())
                .course(subjectRequest.getCourse())
                .creditHour(subjectRequest.getCreditHour())
                .description(subjectRequest.getDescription())
                .learningOutcome(subjectRequest.getLearningOutcome())
                .lecturerName(subjectRequest.getLecturerName())
                .lecturerContact(subjectRequest.getLecturerContact())
                .lecturerEmail(subjectRequest.getLecturerEmail())
                .lecturerOffice(subjectRequest.getLecturerOffice())
                .status(subjectRequest.getStatus()).build());

        assessmentDao.saveAll(
                subjectRequest.getAssessment().stream().map(ass -> {
                    return Assessment.builder()
                            .name(ass.getAssessmentName())
                            .weightage(ass.getAssessmentWeightage())
                            .subject(subject)
                            .build();
                }).toList());
    }

    @Override
    public Subject updateSubject(Long id, SubjectRequest subjectRequest) {
        Subject subject = subjectDAO.findById(id);
        if (subject == null) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Subject not Found");
        }
        List<Assessment> assessmentList = assessmentDao.findBySubjectId(id);
        assessmentDao.saveAll(assessmentList.stream().map(ass -> {
            ass.setGeneralStatus(GeneralStatus.INACTIVE);
            return ass;
        }).toList());

        subject.setCode(subjectRequest.getSubjectCode());
        subject.setName(subjectRequest.getSubjectName());
        subject.setCourse(subjectRequest.getCourse());
        subject.setCreditHour(subjectRequest.getCreditHour());
        subject.setDescription(subjectRequest.getDescription());
        subject.setLearningOutcome(subjectRequest.getLearningOutcome());
        subject.setLecturerName(subjectRequest.getLecturerName());
        subject.setLecturerContact(subjectRequest.getLecturerContact());
        subject.setLecturerEmail(subjectRequest.getLecturerEmail());
        subject.setLecturerOffice(subjectRequest.getLecturerOffice());
        subject.setStatus(subjectRequest.getStatus());
        subject.setAssessment(subjectRequest.getAssessment().stream().map(ass -> {
            return Assessment.builder()
                    .name(ass.getAssessmentName())
                    .generalStatus(GeneralStatus.ACTIVE)
                    .weightage(ass.getAssessmentWeightage())
                    .build();
        }).toList());
        return subjectDAO.save(subject);
    }

    @Override
    public SubjectResponse getSubject(Long subjectId) {
        Subject subject = subjectDAO.findById(subjectId);
        if (subject == null) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Subject not Found");
        }
        return SubjectResponse.builder()
                .subjectCode(subject.getCode())
                .subjectName(subject.getName())
                .course(subject.getCourse())
                .creditHour(subject.getCreditHour())
                .description(subject.getDescription())
                .learningOutcome(subject.getLearningOutcome())
                .lecturerName(subject.getLecturerName())
                .lecturerContact(subject.getLecturerContact())
                .lecturerEmail(subject.getLecturerEmail())
                .lecturerOffice(subject.getLecturerOffice())
                .status(subject.getStatus())
                .assessment(subject.getAssessment().stream()
                        .filter(ass -> ass.getGeneralStatus().equals(GeneralStatus.ACTIVE))
                        .map(ass -> {
                            return AssessmentDto.builder()
                                    .assessmentName(ass.getName())
                                    .assessmentWeightage(ass.getWeightage())
                                    .build();
                        }).toList()).build();
    }

    @Override
    public List<SubjectResponse> getListing() {
        List<Subject> subjectList = subjectDAO.findAll();
        return subjectList.stream().map(subject -> {
            return SubjectResponse.builder()
                    .subjectCode(subject.getCode())
                    .subjectName(subject.getName())
                    .course(subject.getCourse())
                    .creditHour(subject.getCreditHour())
                    .description(subject.getDescription())
                    .learningOutcome(subject.getLearningOutcome())
                    .lecturerName(subject.getLecturerName())
                    .lecturerContact(subject.getLecturerContact())
                    .lecturerEmail(subject.getLecturerEmail())
                    .lecturerOffice(subject.getLecturerOffice())
                    .status(subject.getStatus())
                    .assessment(subject.getAssessment().stream()
                            .filter(ass -> ass.getGeneralStatus().equals(GeneralStatus.ACTIVE))
                            .map(ass -> {
                                return AssessmentDto.builder()
                                        .assessmentName(ass.getName())
                                        .assessmentWeightage(ass.getWeightage())
                                        .build();
                            }).toList()).build();
        }).toList();
    }


}
