package com.chuan.taskmanagement.repository;

import com.chuan.taskmanagement.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
