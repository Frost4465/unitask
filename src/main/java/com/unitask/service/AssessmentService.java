package com.unitask.service;

import com.unitask.dto.subject.AssessmentDto;
import com.unitask.entity.Subject;

import java.util.List;

public interface AssessmentService {

    void update(Subject subject, List<AssessmentDto> dtos);
}
