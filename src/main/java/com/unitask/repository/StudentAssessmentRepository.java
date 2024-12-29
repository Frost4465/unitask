package com.unitask.repository;

import com.unitask.constant.Enum.GeneralStatus;
import com.unitask.dto.assessment.AssessmentSubmissionTuple;
import com.unitask.dto.assessment.AssessmentTuple;
import com.unitask.entity.StudentAssessment;
import com.unitask.entity.User.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

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

    @Query("select s from StudentAssessment s where s.user.id = ?1 and s.assessment.id = ?2")
    StudentAssessment findByUser_IdAndAssessment_Id(Long id, Long id1);

    @Query("select s from StudentAssessment s where s.user.id in ?1 and s.assessment.id = ?2")
    List<StudentAssessment> findByUser_IdInAndAssessment_Id(Collection<Long> ids, Long id);

    @Query("select s from StudentAssessment s where s.assessment.id = ?1")
    List<StudentAssessment> findByAssessment_Id(Long id);

    @Query("SELECT user FROM StudentAssessment user " +
            "WHERE user.id IN :ids ")
    List<StudentAssessment> findAllByIds(@Param("ids") Collection<Long> ids);

}
