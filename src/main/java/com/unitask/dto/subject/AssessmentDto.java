package com.unitask.dto.subject;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AssessmentDto {

    private String assessmentName;
    private String assessmentWeightage;

}
