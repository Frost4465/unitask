package com.unitask.repository;

import com.unitask.dto.studentAssessment.StudentAssessmentTuple;
import com.unitask.entity.StudentAssessment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentAssessmentRepository extends JpaRepository<StudentAssessment, Long> {


    @Query("select s.Id as id," +
            "a.name as name," +
            "s.enrollmentDate as enrolDate," +
            "s.status as status," +
            "sub.name as subjectName" +
            " from StudentAssessment s " +
            "left join Assessment a ON s.assessment.Id = a.Id " +
            "left join Subject sub ON a.subject.Id = sub.Id " +
            "where (?1 is null or a.name like ?1) " +
            "order by s.status DESC")
    Page<StudentAssessmentTuple> findByAssessment_NameOrderByStatusDesc(String name, Pageable pageable);
}
