package com.unitask.controller;

import com.unitask.dto.task.TaskRequest;
import com.unitask.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping(path = "task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody TaskRequest taskRequest) {
        taskService.createTask(taskRequest);
        return ResponseEntity.ok().body("OK");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTask(@PathVariable("id") Long id, @RequestBody TaskRequest taskRequest) {
        taskService.updateTask(id, taskRequest);
        return ResponseEntity.ok().body("OK");
    }

    @PostMapping("/checked/{id}")
    public ResponseEntity<?> checkTask(@PathVariable("id") Long id) {
        taskService.checkTask(id);
        return ResponseEntity.ok().body("OK");
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getTask(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(taskService.getTask(id));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getMyTask() {
        return ResponseEntity.ok().body(taskService.getTaskList());
    }

    @GetMapping("/groupList")
    public ResponseEntity<?> getGroupTask() {
        return ResponseEntity.ok().body(taskService.getGroupTask());
    }
}
