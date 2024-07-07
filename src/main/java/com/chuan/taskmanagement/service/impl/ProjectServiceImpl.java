package com.chuan.taskmanagement.service.impl;

import com.chuan.taskmanagement.constant.ProjectErrorConstant;
import com.chuan.taskmanagement.dao.AppUserDAO;
import com.chuan.taskmanagement.dao.ProjectDAO;
import com.chuan.taskmanagement.dto.PageRequest;
import com.chuan.taskmanagement.dto.project.ProjectRequest;
import com.chuan.taskmanagement.dto.project.ProjectResponse;
import com.chuan.taskmanagement.dto.project.ProjectTuple;
import com.chuan.taskmanagement.entity.AppUser;
import com.chuan.taskmanagement.entity.Project;
import com.chuan.taskmanagement.entity.ProjectMember;
import com.chuan.taskmanagement.exception.ServiceAppException;
import com.chuan.taskmanagement.mapper.ProjectMapper;
import com.chuan.taskmanagement.service.ContextService;
import com.chuan.taskmanagement.service.ProjectService;
import com.chuan.taskmanagement.util.PageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class ProjectServiceImpl extends ContextService implements ProjectService {

    @Autowired
    private ProjectDAO projectDAO;
    @Autowired
    private AppUserDAO appUserDAO;
    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public void createProject(ProjectRequest projectRequest) {
        AppUser leader = appUserDAO.findById(projectRequest.getLeadId());
        List<AppUser> members = appUserDAO.findByIds(projectRequest.getParticipantIds());

        if (projectDAO.isCodeExist(projectRequest.getCode())) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, ProjectErrorConstant.KEY_DUP);
        }

        Project project = new Project();
        project.setName(projectRequest.getName());
        project.setCode(projectRequest.getCode());
        project.setDescription(projectRequest.getDescription());
        project.setLeader(leader);

        if (CollectionUtils.isNotEmpty(members)) {
            Set<ProjectMember> projectMembers = new HashSet<>();
            for (AppUser appUser : members) {
                ProjectMember projectMember = new ProjectMember();
                projectMember.setProject(project);
                projectMember.setAppUser(appUser);
                projectMembers.add(projectMember);
            }
            project.setProjectMembers(projectMembers);
        }

        projectDAO.save(project);
    }

    @Override
    public void updateProject(Long id,ProjectRequest request) {

        Project project = projectDAO.findById(id);
        List<AppUser> members = appUserDAO.findByIds(request.getParticipantIds());
        AppUser leader = appUserDAO.findById(request.getLeadId());

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setLeader(leader);

        Set<ProjectMember> projectMembers;
        if (Objects.isNull(project.getProjectMembers())) {
            projectMembers = new HashSet<>();
        } else {
            projectMembers = project.getProjectMembers();
        }
        for (AppUser appUser : members) {
            if (!members.contains(appUser)) {
                ProjectMember projectMember = new ProjectMember();
                projectMember.setProject(project);
                projectMember.setAppUser(appUser);
                projectMembers.add(projectMember);
            }
        }
        projectMembers.removeIf(x -> !members.contains(x.getAppUser()));
        projectDAO.save(project);
    }

    @Override
    public void deleteProject(Long id) {
        Project project = projectDAO.findById(id);
        projectDAO.delete(project);
    }

    @Override
    public PagedModel<ProjectTuple> listProject(PageRequest pageRequest) {
        Pageable pageable = PageUtil.pageable(pageRequest);
        String likeSearch = PageUtil.likeSearch(pageRequest.getSearchQuery());
        return new PagedModel<>(projectDAO.list(pageable, likeSearch));
    }

    @Override
    public ProjectResponse readProject(Long id) {
        return projectMapper.toResponse(projectDAO.findById(id));
    }
}
