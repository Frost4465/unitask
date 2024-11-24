package com.unitask.service.impl;

import com.unitask.dao.AppUserDAO;
import com.unitask.dao.SubjectDAO;
import com.unitask.dto.subject.SubjectRequest;
import com.unitask.entity.Subject;
import com.unitask.entity.User.AppUser;
import com.unitask.exception.ServiceAppException;
import com.unitask.service.SubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    SubjectDAO subjectDAO;
    AppUserDAO appUserDAO;

    SubjectServiceImpl(SubjectDAO subjectDAO, AppUserDAO appUserDAO) {
        this.subjectDAO = subjectDAO;
        this.appUserDAO = appUserDAO;
    }

    @Override
    public void create(SubjectRequest subjectRequest) {
        AppUser appUser = appUserDAO.findById(subjectRequest.getLecturerId());
        if (appUser == null) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Lecturer does not exist");
        }
        subjectDAO.save(Subject.builder()
                .name(subjectRequest.getName())
                .lecturer(appUser)
                .status(subjectRequest.getStatus())
                .build());
    }

    @Override
    public Subject updateSubject(Long id, SubjectRequest subjectRequest) {
        Subject subject = subjectDAO.findById(id);
        if (subject == null) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Subject not Found");
        }
        AppUser appUser = appUserDAO.findById(subjectRequest.getLecturerId());
        if (appUser == null) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Lecturer does not exist");
        }
        subject.setLecturer(appUser);
        subject.setName(subjectRequest.getName());
        subject.setStatus(subjectRequest.getStatus());
        return subjectDAO.save(subject);
    }

    @Override
    public Subject getSubject(Long subjectId) {
        Subject subject = subjectDAO.findById(subjectId);
        if (subject == null) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Subject not Found");
        }
        return subject;
    }

    @Override
    public List<Subject> getListing() {
        return subjectDAO.findAll();
    }


}
