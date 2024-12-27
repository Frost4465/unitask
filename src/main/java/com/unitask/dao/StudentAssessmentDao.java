package com.unitask.dao;

import com.unitask.dto.assessment.AssessmentTuple;
import com.unitask.entity.StudentAssessment;
import com.unitask.exception.ServiceAppException;
import com.unitask.repository.StudentAssessmentRepository;
import com.unitask.util.PageUtil;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

@Service
public class StudentAssessmentDao {

    @Autowired
    private StudentAssessmentRepository studentAssessmentRepository;

    public List<StudentAssessment> saveAll(Collection<StudentAssessment> studentAssessmentList) {
        if (CollectionUtils.isEmpty(studentAssessmentList)) {
            return null;
        }
        return studentAssessmentRepository.saveAll(studentAssessmentList);
    }

    public Page<AssessmentTuple> getAssessmentListing(String search, Pageable pageable) {
        String filter = search;
        if (StringUtils.isNotBlank(search)) {
            filter = PageUtil.likeSearch(search);
        }
        return studentAssessmentRepository.findByAssessment_NameOrderByStatusDesc(filter, pageable);
    }

    public StudentAssessment findById(Long id) {
        return studentAssessmentRepository.findById(id).orElseThrow(() -> new ServiceAppException(HttpStatus.BAD_REQUEST, "Not found"));
    }

    public StudentAssessment save(StudentAssessment assessment) {
        return studentAssessmentRepository.save(assessment);
    }
}
