package com.chuan.taskmanagement.repository;

import com.chuan.taskmanagement.dto.project.ProjectTuples;
import com.chuan.taskmanagement.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p.id as id , " +
            "p.name as name, " +
            "p.leader.name as leaderName " +
            "FROM Project p")
    Page<ProjectTuples> list(Pageable pageable);
}
