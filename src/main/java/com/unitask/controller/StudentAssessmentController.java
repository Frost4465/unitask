package com.unitask.controller;

import com.unitask.dto.PageRequest;
import com.unitask.service.StudentAssessmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(path = "student-assessment")
@AllArgsConstructor
public class StudentAssessmentController {

    private final StudentAssessmentService studentAssessmentService;

    @GetMapping("/assessment/list")
    public ResponseEntity<?> getAssessmentListing(PageRequest pageRequest) {
        return ResponseEntity.ok().body(studentAssessmentService.getAssessmentListing(pageRequest));
    }
}
