package com.chuan.taskmanagement.service.impl;

import com.chuan.taskmanagement.dao.AppUserDAO;
import com.chuan.taskmanagement.dao.ProjectDAO;
import com.chuan.taskmanagement.dto.project.CreateProjectRequest;
import com.chuan.taskmanagement.dto.project.UpdateProjectRequest;
import com.chuan.taskmanagement.entity.AppUser;
import com.chuan.taskmanagement.entity.Project;
import com.chuan.taskmanagement.exception.ServiceAppException;
import com.chuan.taskmanagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDAO projectDAO;
    @Autowired
    private AppUserDAO appUserDAO;

    @Override
    public void createProject(CreateProjectRequest projectRequest) {
        checkUserExists(projectRequest.getParticipants());
        projectDAO.save(Project.builder()
                .name(projectRequest.getName())
                .description(projectRequest.getDescription())
                .members(projectRequest.getParticipants())
                .build());
    }

    @Override
    public void updateProject(UpdateProjectRequest projectRequest) {
        checkUserExists(projectRequest.getParticipants());
        Project project = projectDAO.findById(projectRequest.getId());
        project.setMembers(projectRequest.getParticipants());
        projectDAO.save(project);
    }

    @Override
    public void deleteProject(Long id) {
        Project project = projectDAO.findById(id);
        project.setDeleted(true);
        projectDAO.save(project);

    }

    private void checkUserExists(List<String> users){
        if (!CollectionUtils.isEmpty(users)) {
            List<AppUser> appUserList = appUserDAO.findAllByEmail(users);
            if (appUserList.size() != users.size()) {
                throw new ServiceAppException(HttpStatus.BAD_REQUEST, "User Email Invalid");
            }
        }
    }


}
