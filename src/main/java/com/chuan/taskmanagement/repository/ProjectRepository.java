package com.chuan.taskmanagement.repository;

import com.chuan.taskmanagement.dto.project.ProjectTuples;
import com.chuan.taskmanagement.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p.id as id , " +
            "p.name as name, " +
            "leader as leaderName " +
            "FROM Project p " +
            "LEFT JOIN p.leader leader " +
            "WHERE :search IS NULL or p.name LIKE :search ")
    Page<ProjectTuples> list(Pageable pageable, @Param("search") String search);
}
