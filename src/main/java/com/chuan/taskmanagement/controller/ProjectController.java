package com.chuan.taskmanagement.controller;

import com.chuan.taskmanagement.dto.project.CreateProjectRequest;
import com.chuan.taskmanagement.dto.project.UpdateProjectRequest;
import com.chuan.taskmanagement.dto.ticket.CreateTicketRequest;
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
    public ResponseEntity<?> createTicket(@Valid @RequestBody CreateProjectRequest projectRequest) {
        projectService.createProject(projectRequest);
        return ResponseEntity.ok("Project created");
    }

    @PutMapping("/updateProject")
    public ResponseEntity<?> updateProject(@Valid @RequestBody UpdateProjectRequest updateProjectRequest) {
        projectService.updateProject(updateProjectRequest);
        return ResponseEntity.ok("Project updated");
    }

    @DeleteMapping("/deleteProject/{id}")
    public ResponseEntity<?> deleteProject(@Valid @PathVariable("id") Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok("Project deleted");
    }

}
