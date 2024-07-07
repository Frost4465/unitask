package com.chuan.taskmanagement.dao;

import com.chuan.taskmanagement.constant.ProjectErrorConstant;
import com.chuan.taskmanagement.dto.project.ProjectTuple;
import com.chuan.taskmanagement.entity.Project;
import com.chuan.taskmanagement.exception.ServiceAppException;
import com.chuan.taskmanagement.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ProjectDAO {

    @Autowired
    private ProjectRepository projectRepository;

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public void delete(Project project) {
        projectRepository.delete(project);
    }

    public Project findById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new ServiceAppException(HttpStatus.BAD_REQUEST, ProjectErrorConstant.NOT_FOUND));
    }

    public boolean isCodeExist(String key) {
        if (Objects.isNull(key)) {
            return true;
        }
        return projectRepository.findByCode(key).isPresent();
    }

    public Page<ProjectTuple> list(Pageable pageable, String search) {
        return projectRepository.list(pageable, search);
    }
}
