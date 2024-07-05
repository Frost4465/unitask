package com.chuan.taskmanagement.service;

import com.chuan.taskmanagement.dto.BaseViewOption;
import com.chuan.taskmanagement.dto.project.CreateProjectRequest;
import com.chuan.taskmanagement.dto.project.ProjectResponse;
import com.chuan.taskmanagement.dto.project.ProjectTuples;
import com.chuan.taskmanagement.dto.project.UpdateProjectRequest;
import org.springframework.data.domain.Page;

public interface ProjectService {

    void createProject(CreateProjectRequest projectRequest);

    void updateProject(UpdateProjectRequest projectRequest);

    void deleteProject(Long id);

    Page<ProjectTuples> listProject(BaseViewOption baseViewOption);

    ProjectResponse readProject(Long id);

}
