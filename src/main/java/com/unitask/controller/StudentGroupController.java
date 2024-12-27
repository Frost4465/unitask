package com.unitask.controller;

import com.amazonaws.Response;
import com.unitask.service.StudentGroupService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Validated
@RequestMapping(path = "student-group")
@AllArgsConstructor
public class StudentGroupController {

    @Autowired
    private StudentGroupService studentGroupService;

    @PostMapping("/join/{id}")
    public ResponseEntity<?> joinGroup(@RequestParam("id") Long id) {
        studentGroupService.joinGroup(id);
        return ResponseEntity.ok().body("OK");
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<?> submitGroup(@RequestParam("id") Long id, @RequestBody MultipartFile multipartFile) {
        studentGroupService.submitGroup(id, multipartFile);
        return ResponseEntity.ok().body("OK");
    }

    @PostMapping("/leave/{id}")
    public ResponseEntity<?> leaveGroup(@RequestParam("id") Long id) {
        studentGroupService.leaveGroup(id);
        return ResponseEntity.ok().body("OK");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGroup(@PathVariable Long id) {
        return ResponseEntity.ok().body(studentGroupService.getGroup(id));
    }


}
