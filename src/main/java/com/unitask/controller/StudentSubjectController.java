package com.unitask.controller;

import com.unitask.service.StudentSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping(path = "student-subject")
public class StudentSubjectController {

    @Autowired
    private StudentSubjectService studentSubjectService;

    @GetMapping("/list")
    public ResponseEntity<?> getListing() {
        return ResponseEntity.ok().body(studentSubjectService.getListing());
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> enroll(@PathVariable Long id) {
        return ResponseEntity.ok().body(studentSubjectService.enroll(id));
    }
}
