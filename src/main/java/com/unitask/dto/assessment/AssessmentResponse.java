package com.unitask.dto.assessment;

import com.unitask.dto.FileResponse;
import com.unitask.dto.subject.SubjectResponse;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AssessmentResponse {

    private String name;
    private String weightage;
    private SubjectResponse subject;
    private String assignmentMode;
    private LocalDate dueDate;
    private String lecturerInstruction;
    private List<AssessmentMarkingRubricDto> assessmentMarkingRubrics;
    private List<FileResponse> attachedFile;

}
