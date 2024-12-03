package com.unitask.controller;

import com.unitask.dto.studentAssessment.StudentAssessmentTuple;
import com.unitask.service.StudentSubjectService;
import com.unitask.util.PageWrapperVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @GetMapping("/assessment/list")
    public ResponseEntity<?> getAssessmentListing(String search) {
        return ResponseEntity.ok().body(studentSubjectService.getAssessmentListing(search));
    }

}
