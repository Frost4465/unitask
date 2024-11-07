package com.unitask.service.impl;

import com.unitask.constant.error.ProjectErrorConstant;
import com.unitask.dao.AppUserDAO;
import com.unitask.dao.ProjectDAO;
import com.unitask.dto.PageRequest;
import com.unitask.dto.project.ProjectRequest;
import com.unitask.dto.project.ProjectResponse;
import com.unitask.dto.project.ProjectTuple;
import com.unitask.entity.AppUser;
import com.unitask.entity.Project;
import com.unitask.entity.ProjectMember;
import com.unitask.exception.ServiceAppException;
import com.unitask.mapper.ProjectMapper;
import com.unitask.service.ContextService;
import com.unitask.service.ProjectService;
import com.unitask.util.PageUtil;
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
import java.util.stream.Collectors;

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
        List<AppUser> members = appUserDAO.findByIds(projectRequest.getProjectMemberIds());

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
    public void updateProject(Long id, ProjectRequest request) {

        Project project = projectDAO.findById(id);
        List<AppUser> members = appUserDAO.findByIds(request.getProjectMemberIds());
        AppUser leader = appUserDAO.findById(request.getLeadId());

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setLeader(leader);

        Set<ProjectMember> projectMembers;
        if (Objects.isNull(project.getProjectMembers())) {
            projectMembers = new HashSet<>();
            project.setProjectMembers(projectMembers);
            for (AppUser appUser : members) {
                ProjectMember projectMember = new ProjectMember();
                projectMember.setProject(project);
                projectMember.setAppUser(appUser);
                projectMembers.add(projectMember);
            }
        } else {
            projectMembers = project.getProjectMembers();
            Set<AppUser> existingAppUser = projectMembers.stream().map(ProjectMember::getAppUser).collect(Collectors.toSet());
            for (AppUser appUser : members) {
                if (!existingAppUser.contains(appUser)) {
                    ProjectMember projectMember = new ProjectMember();
                    projectMember.setProject(project);
                    projectMember.setAppUser(appUser);
                    projectMembers.add(projectMember);
                }
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
        AppUser appUser = appUserDAO.findByEmail(getCurrentAuthUsername());
        Pageable pageable = PageUtil.pageable(pageRequest);
        String likeSearch = PageUtil.likeSearch(pageRequest.getSearchQuery());
        return new PagedModel<>(projectDAO.list(pageable, likeSearch, appUser.getId()));
    }

    @Override
    public ProjectResponse readProject(Long id) {
        return projectMapper.toResponse(projectDAO.findById(id));
    }
}
