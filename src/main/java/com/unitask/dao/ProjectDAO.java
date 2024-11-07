package com.unitask.dao;

import com.unitask.constant.error.ProjectErrorConstant;
import com.unitask.dto.project.ProjectTuple;
import com.unitask.entity.Project;
import com.unitask.exception.ServiceAppException;
import com.unitask.repository.ProjectRepository;
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

    public Page<ProjectTuple> list(Pageable pageable, String search, Long userId) {
        return projectRepository.list(pageable, search, userId);
    }

    public Long countMyProject(Long userId) {
        return projectRepository.countByProjectMembers_AppUser_Id(userId);
    }
}
