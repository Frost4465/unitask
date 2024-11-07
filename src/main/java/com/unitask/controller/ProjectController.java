package com.unitask.controller;

import com.unitask.dto.PageRequest;
import com.unitask.dto.project.ProjectRequest;
import com.unitask.dto.project.ProjectResponse;
import com.unitask.dto.project.ProjectTuple;
import com.unitask.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping(path = "project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/student/createProject")
    public ResponseEntity<String> createTicket(@Valid @RequestBody ProjectRequest projectRequest) {
        projectService.createProject(projectRequest);
        return ResponseEntity.ok("Project created");
    }

    @PutMapping("/updateProject/{id}")
    public ResponseEntity<String> updateProject(@PathVariable("id") Long id, @Valid @RequestBody ProjectRequest projectRequest) {
        projectService.updateProject(id, projectRequest);
        return ResponseEntity.ok("Project updated");
    }

    @DeleteMapping("/deleteProject/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable("id") Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok("Project deleted");
    }

    @GetMapping("/listProject")
    public ResponseEntity<PagedModel<ProjectTuple>> listProject(PageRequest pageRequest) {
        return ResponseEntity.ok(projectService.listProject(pageRequest));
    }

    @GetMapping("/readProject/{id}")
    public ResponseEntity<ProjectResponse> readProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.readProject(id));
    }
}
