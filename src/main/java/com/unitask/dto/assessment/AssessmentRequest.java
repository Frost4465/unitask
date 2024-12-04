package com.unitask.dto.assessment;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AssessmentRequest {

    private String name;
    private String weightage;
    private String assignmentMode;
    private LocalDate dueDate;
    private String lecturerInstruction;
    private List<AssessmentMarkingRubricDto> assessmentMarkingRubrics;
}
