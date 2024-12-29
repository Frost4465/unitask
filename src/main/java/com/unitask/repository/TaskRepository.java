package com.unitask.repository;

import com.unitask.entity.Group;
import com.unitask.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("select t from Task t where t.user.id = ?1")
    List<Task> findByUser_Id(Long id);

    @Query("select t from Task t where t.assessment.id in ?1")
    List<Task> findByAssessment_IdIn(Collection<Long> ids);

    @Query("""
            select t from Task t inner join t.assessment.studentAssessments studentAssessments
            where studentAssessments.group.id in ?1 and t.assessment.id in ?2""")
    List<Task> findByAssessment_StudentAssessments_Group_IdInAndAssessment_IdIn(Collection<Long> ids, Collection<Long> ids1);


}
