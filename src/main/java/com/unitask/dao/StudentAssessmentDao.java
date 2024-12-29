package com.unitask.dao;

import com.unitask.dto.assessment.AssessmentTuple;
import com.unitask.entity.StudentAssessment;
import com.unitask.exception.ServiceAppException;
import com.unitask.repository.StudentAssessmentRepository;
import com.unitask.util.PageUtil;
import io.micrometer.common.util.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<StudentAssessment> findByAssignment(Long id) {
        return studentAssessmentRepository.findByAssessment_Id(id);
    }

    public StudentAssessment findByAssessmentAndAppUser(Long appUserId, Long assessmentId) {
        return studentAssessmentRepository.findByUser_IdAndAssessment_Id(appUserId, assessmentId);
    }

    public List<StudentAssessment> findByAssessmentAndAppUserList(List<Long> appUserIdList, Long assessmentId) {
        return studentAssessmentRepository.findByUser_IdInAndAssessment_Id(appUserIdList, assessmentId);
    }

    public List<StudentAssessment> findByIds(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        return studentAssessmentRepository.findAllByIds(ids);
    }

}
