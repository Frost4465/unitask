package com.unitask.dao;

import com.unitask.entity.assessment.Assessment;
import com.unitask.entity.assessment.AssessmentFile;
import com.unitask.exception.ServiceAppException;
import com.unitask.repository.AssessmentFileRepository;
import com.unitask.repository.AssessmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class AssessmentDao {

    private final AssessmentRepository assessmentRepository;
    private final AssessmentFileRepository assessmentFileRepository;

    public List<Assessment> saveAll(Collection<Assessment> assessmentList) {
        if (CollectionUtils.isEmpty(assessmentList)) {
            return null;
        }
        return assessmentRepository.saveAll(assessmentList);
    }

    public void deleteAll(Collection<Assessment> assessmentList) {
        if (CollectionUtils.isEmpty(assessmentList)) {
            return;
        }
        assessmentRepository.deleteAll(assessmentList);
    }

    public Assessment findById(Long id) {
        if (id == null) {
            return null;
        }
        return assessmentRepository.findById(id).orElseThrow(() -> new ServiceAppException(HttpStatus.BAD_REQUEST, "NOT_FOUND"));
    }

    public AssessmentFile saveFile(AssessmentFile assessmentFile) {
        return assessmentFileRepository.save(assessmentFile);
    }

    public void deleteFile(AssessmentFile assessmentFile) {
        assessmentFileRepository.delete(assessmentFile);
    }

    public AssessmentFile findFileByFileId(Long id) {
        if (id == null) {
            return null;
        }
        return assessmentFileRepository.findById(id).orElseThrow(() -> new ServiceAppException(HttpStatus.BAD_REQUEST, "NOT_FOUND"));
    }

}
