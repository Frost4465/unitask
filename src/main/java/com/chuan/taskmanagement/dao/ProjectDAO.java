package com.chuan.taskmanagement.dao;

import com.chuan.taskmanagement.dto.project.ProjectTuples;
import com.chuan.taskmanagement.entity.Project;
import com.chuan.taskmanagement.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProjectDAO {

    @Autowired
    private ProjectRepository projectRepository;

    public void save(Project project) {
        projectRepository.save(project);
    }

    public Project findById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    public Page<ProjectTuples> list(Pageable pageable) {
        return projectRepository.list(pageable);
    }
}
