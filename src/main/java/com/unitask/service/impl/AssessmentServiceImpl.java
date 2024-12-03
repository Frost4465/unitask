package com.unitask.service.impl;


import com.unitask.constant.Enum.GeneralStatus;
import com.unitask.dao.AssessmentDao;
import com.unitask.dto.subject.AssessmentDto;
import com.unitask.entity.Assessment;
import com.unitask.entity.Subject;
import com.unitask.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssessmentServiceImpl implements AssessmentService {

    private final AssessmentDao assessmentDao;

    public void update(Subject subject, List<AssessmentDto> dtos) {
        Map<Long, Assessment> map = subject.getAssessment()
                .stream()
                .collect(Collectors.toMap(x -> x.getId(), x -> x));

        List<Assessment> saveThis = new ArrayList<>();
        for (AssessmentDto dto : dtos) {
            Assessment assessment;
            if (Objects.nonNull(dto.getId()) && Objects.nonNull(map.get(dto.getId()))) {
                //update
                assessment = map.get(dto.getId());
                map.remove(dto.getId());
            } else {
                //create
                assessment = new Assessment();
            }
            assessment.setName(dto.getName());
            assessment.setWeightage(dto.getWeightage());
            assessment.setSubject(subject);
            assessment.setGeneralStatus(GeneralStatus.ACTIVE);
            saveThis.add(assessment);
        }
        //delete
        assessmentDao.deleteAll(map.values());
        assessmentDao.saveAll(saveThis);
    }

}
