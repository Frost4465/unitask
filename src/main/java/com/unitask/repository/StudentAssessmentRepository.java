package com.unitask.repository;

import com.unitask.constant.Enum.GeneralStatus;
import com.unitask.dto.assessment.AssessmentSubmissionTuple;
import com.unitask.dto.assessment.AssessmentTuple;
import com.unitask.entity.StudentAssessment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentAssessmentRepository extends JpaRepository<StudentAssessment, Long> {


    @Query("select s.id as id," +
            "a.name as name," +
            "a.dueDate as dueDate," +
            "s.status as status," +
            "sub.name as subjectName" +
            " from StudentAssessment s " +
            "left join Assessment a ON s.assessment.id = a.id " +
            "left join Subject sub ON a.subject.id = sub.id " +
            "where (?1 is null or a.name like ?1) " +
            "order by s.status DESC")
    Page<AssessmentTuple> findByAssessment_NameOrderByStatusDesc(String name, Pageable pageable);
}
