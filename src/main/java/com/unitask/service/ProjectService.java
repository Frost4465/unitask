package com.unitask.service;

import com.unitask.dto.PageRequest;
import com.unitask.dto.project.ProjectRequest;
import com.unitask.dto.project.ProjectResponse;
import com.unitask.dto.project.ProjectTuple;
import org.springframework.data.web.PagedModel;

public interface ProjectService {

    void createProject(ProjectRequest projectRequest);

    void updateProject(Long id, ProjectRequest projectRequest);

    void deleteProject(Long id);

    PagedModel<ProjectTuple> listProject(PageRequest pageRequest);

    ProjectResponse readProject(Long id);

}
