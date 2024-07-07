package com.chuan.taskmanagement.controller;

import com.chuan.taskmanagement.dto.PageRequest;
import com.chuan.taskmanagement.dto.PageResponse;
import com.chuan.taskmanagement.dto.project.CreateProjectRequest;
import com.chuan.taskmanagement.dto.project.ProjectResponse;
import com.chuan.taskmanagement.dto.project.ProjectTuples;
import com.chuan.taskmanagement.dto.project.UpdateProjectRequest;
import com.chuan.taskmanagement.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping(path = "project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/createProject")
    public ResponseEntity<String> createTicket(@Valid @RequestBody CreateProjectRequest projectRequest) {
        projectService.createProject(projectRequest);
        return ResponseEntity.ok("Project created");
    }

    @PutMapping("/updateProject")
    public ResponseEntity<String> updateProject(@Valid @RequestBody UpdateProjectRequest updateProjectRequest) {
        projectService.updateProject(updateProjectRequest);
        return ResponseEntity.ok("Project updated");
    }

    @DeleteMapping("/deleteProject/{id}")
    public ResponseEntity<String> deleteProject(@Valid @PathVariable("id") Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok("Project deleted");
    }

    @GetMapping("/listProject")
    public ResponseEntity<PageResponse<ProjectTuples>> listProject(PageRequest pageRequest) {
        return ResponseEntity.ok(projectService.listProject(pageRequest));
    }

    @GetMapping("/readProject/{id}")
    public ResponseEntity<ProjectResponse> readProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.readProject(id));
    }
}
