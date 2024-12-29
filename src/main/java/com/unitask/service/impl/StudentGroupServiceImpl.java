package com.unitask.service.impl;

import com.unitask.dao.AppUserDAO;
import com.unitask.dao.GroupDao;
import com.unitask.dao.StudentAssessmentDao;
import com.unitask.dto.group.GroupResponse;
import com.unitask.entity.Group;
import com.unitask.entity.StudentAssessment;
import com.unitask.entity.User.AppUser;
import com.unitask.exception.ServiceAppException;
import com.unitask.mapper.GroupMapper;
import com.unitask.service.ContextService;
import com.unitask.service.StudentGroupService;
import com.unitask.util.OssUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class StudentGroupServiceImpl extends ContextService implements StudentGroupService {

    @Autowired
    private GroupDao groupDao;
    @Autowired
    private AppUserDAO appUserDAO;
    @Autowired
    private StudentAssessmentDao studentAssessmentDao;
    @Autowired
    private OssUtil ossUtil;

    @Override
    public void joinGroup(Long id) {
        AppUser appUser = appUserDAO.findByEmail(getCurrentAuthUsername());
        Group group = groupDao.findById(id);
        if (group == null) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Group does not Exists");
        }
        StudentAssessment studentAssessment = studentAssessmentDao.findByAssessmentAndAppUser(group.getAssessment().getId(), appUser.getId());
        studentAssessment.setGroup(group);
        studentAssessmentDao.save(studentAssessment);
    }

    @Override
    public void submitGroup(Long id, MultipartFile multipartFile) {
        Group group = groupDao.findById(id);
        if (group == null) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Group does not Exists");
        }
        if (StringUtils.isNotBlank(group.getFilePath())) {
            ossUtil.removeObject(group.getFilePath());
        }
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String uuid = UUID.randomUUID().toString();
        String path = "group" + "/" + uuid + "." + extension;
        ossUtil.putObject(path, multipartFile);
        group.setFileName(multipartFile.getOriginalFilename());
        group.setFilePath(path);
        group.setUuid(uuid);
        group.setFileCreatedDate(LocalDateTime.now());
        groupDao.save(group);
    }

    @Override
    public void leaveGroup(Long id) {
        AppUser appUser = appUserDAO.findByEmail(getCurrentAuthUsername());
        Group group = groupDao.findById(id);
        if (group == null) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Group does not Exists");
        }
        StudentAssessment studentAssessment = group.getStudentAssessment().stream().filter(ass -> ass.getUser().getId().equals(appUser.getId())).findFirst().orElse(null);
        if (studentAssessment == null) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Student Assessment does not Exists");
        }
        studentAssessment.setGroup(null);
        studentAssessmentDao.save(studentAssessment);
    }

    @Override
    public GroupResponse getGroup(Long id) {
        Group group = groupDao.findById(id);
        if (group == null) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Group does not Exists");
        }
        return GroupMapper.INSTANCE.toResponse(group);
    }
}