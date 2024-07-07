package com.chuan.taskmanagement.repository;

import com.chuan.taskmanagement.dto.project.ProjectTuple;
import com.chuan.taskmanagement.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p.id as id , " +
            "p.name as name, " +
            "p.code as code, " +
            "leader.name as leaderName " +
            "FROM Project p " +
            "LEFT JOIN p.leader leader " +
            "WHERE (:search IS NULL or p.name LIKE :search) ")
    Page<ProjectTuple> list(Pageable pageable, @Param("search") String search);

    @Query("select p from Project p where p.code = :code")
    Optional<Project> findByCode(@Param("code") String code);

}
