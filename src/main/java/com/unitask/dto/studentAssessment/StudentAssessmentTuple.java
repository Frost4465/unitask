package com.unitask.dto.studentAssessment;

import com.unitask.constant.Enum.GeneralStatus;

import java.time.LocalDate;

public interface StudentAssessmentTuple {

    Long getId();
    String getName();
    LocalDate getEnrolDate();
    String getStatus();
    String getSubjectName();

}
