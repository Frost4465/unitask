package com.chuan.taskmanagement.mapper;

import com.chuan.taskmanagement.dto.DropdownResponse;
import com.chuan.taskmanagement.dto.project.ProjectResponse;
import com.chuan.taskmanagement.entity.Project;
import com.chuan.taskmanagement.entity.ProjectMember;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class ProjectMapper {

    @Autowired
    private UserMapper userMapper;

    public ProjectResponse toResponse(Project project) {
        if (Objects.isNull(project)) {
            return null;
        }

        ProjectResponse projectResponse = new ProjectResponse();
        projectResponse.setId(project.getId());
        projectResponse.setName(project.getName());
        projectResponse.setCode(project.getCode());
        projectResponse.setDescription(project.getDescription());
        projectResponse.setLeader(userMapper.toDropdown(project.getLeader()));
        projectResponse.setProjectMembers(toDropdown(project.getProjectMembers()));

        return projectResponse;
    }

    public DropdownResponse toDropdown(Project project) {
        if (Objects.isNull(project)) {
            return null;
        }
        DropdownResponse dropdownResponse = new DropdownResponse();
        dropdownResponse.setId(project.getId());
        dropdownResponse.setLabel(project.getName());
        return dropdownResponse;
    }

    public List<DropdownResponse> toDropdown(Collection<ProjectMember> projectMembers) {
        if (CollectionUtils.isEmpty(projectMembers)) {
            return null;
        }

        List<DropdownResponse> dropdownResponseList = new ArrayList<>();
        for (ProjectMember projectMember : projectMembers) {
            dropdownResponseList.add(toDropdown(projectMember));
        }
        return dropdownResponseList;
    }

    public DropdownResponse toDropdown(ProjectMember projectMember) {
        if (Objects.isNull(projectMember)) {
            return null;
        }
        DropdownResponse dropdownResponse = new DropdownResponse();
        dropdownResponse.setId(projectMember.getAppUser().getId());
        dropdownResponse.setLabel(projectMember.getAppUser().getName());
        return dropdownResponse;
    }
}
