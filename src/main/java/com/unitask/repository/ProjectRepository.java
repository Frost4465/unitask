package com.unitask.repository;

import com.unitask.dto.project.ProjectTuple;
import com.unitask.entity.Project;
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
            "WHERE (:search IS NULL or p.name LIKE :search) " +
            "AND (:userId IS NULL OR leader.Id = :userId or ((SELECT count(pm) FROM ProjectMember pm WHERE pm.appUser.Id = :userId AND pm.project.id = p.id)> 0))")
    Page<ProjectTuple> list(Pageable pageable, @Param("search") String search, @Param("userId") Long userId);

    @Query("select p from Project p where p.code = :code")
    Optional<Project> findByCode(@Param("code") String code);

    @Query(" select count(p) from Project p inner join p.projectMembers projectMembers " +
            " where projectMembers.appUser.Id = :Id")
    long countByProjectMembers_AppUser_Id(@Param("Id") Long Id);
}
