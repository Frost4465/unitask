package com.unitask.controller;

import com.unitask.dto.PageRequest;
import com.unitask.service.AssessmentSubmissionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(path = "assessment-submission")
@AllArgsConstructor
public class AssessmentSubmissionController {

    private final AssessmentSubmissionService assessmentSubmissionService;

    @GetMapping("/list")
    public ResponseEntity<?> getListing(PageRequest pageRequest) {
        return ResponseEntity.ok().body(assessmentSubmissionService.getListing(pageRequest));
    }

    @GetMapping("/resubmit/{id}")
    public ResponseEntity<?> resubmit(Long id) {
        return ResponseEntity.ok().body(assessmentSubmissionService.resubmit(id));
    }
}
