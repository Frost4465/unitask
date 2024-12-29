package com.unitask.repository;

import com.unitask.dto.assessment.AssessmentSubmissionTuple;
import com.unitask.entity.assessment.AssessmentSubmission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AssessmentSubmissionRepository extends JpaRepository<AssessmentSubmission, Long> {
    @Query("select asub.id as id," +
            "ass.name as assignmentName," +
            "asub.submissionDate as submissionDate," +
            "g.name as groupName," +
            "sub.name as subjectName, " +
            "sub.code as subjectCode " +
            "from AssessmentSubmission asub " +
            "left join asub.assessment ass " +
            "left join ass.subject sub " +
            "left join asub.group g " +
            "where (sub.owner.id = :ownerId) and " +
            "(:assignment is null or ass.name like :assignment) and " +
            "(:group is null or g.name like :group) and " +
            "(:subject is null or sub.code like :subject) and " +
            "(:beforeDate is null or asub.submissionDate >= :beforeDate) and " +
            "(:afterDate is null or asub.submissionDate <= :afterDate) " +
            "order by asub.submissionDate DESC")
    Page<AssessmentSubmissionTuple> getAssessmentSubmissionListing(Long ownerId, String assignment, String group, String subject, LocalDateTime beforeDate, LocalDateTime afterDate, Pageable pageable);

    @Query(value = "SELECT DISTINCT a.*\n" +
            "FROM assessment_submission a\n" +
            "INNER JOIN student_assessment asa ON a.assessment_id = asa.assessment_id\n" +
            "INNER JOIN assessment ass ON a.assessment_id = ass.id\n" +
            "INNER JOIN subject s ON s.id = ass.subject_id\n" +
            "WHERE a.group_id IN (:groupId)\n" +
            "  AND (:assName IS NULL OR ass.name LIKE :assName)\n" +
            "  AND (:subName IS NULL OR s.name LIKE :subName)\n" +
            "  AND (:beforeDate < a.submission_date)\n" +
            "  AND (:afterDate > a.submission_date)\n" +
            "UNION\n" +
            "SELECT DISTINCT a.*\n" +
            "FROM assessment_submission a\n" +
            "INNER JOIN student_assessment asa ON a.assessment_id = asa.assessment_id\n" +
            "INNER JOIN assessment ass ON a.assessment_id = ass.id\n" +
            "INNER JOIN subject s ON s.id = ass.subject_id\n" +
            "WHERE asa.user_id = :userId\n" +
            "  AND a.group_id IS NULL\n" +
            "  AND (:assName IS NULL OR ass.name LIKE :assName)\n" +
            "  AND (:subName IS NULL OR s.name LIKE :subName)\n" +
            "  AND (:beforeDate < a.submission_date)\n" +
            "  AND (:afterDate > a.submission_date);", nativeQuery = true)
    Page<AssessmentSubmission> findByGroupIdUnionIndividual(@Param("groupId") List<Long> groupId, @Param("userId") Long userId,
                                                            @Param("assName") String assName, @Param("subName") String subName,
                                                            @Param("beforeDate") LocalDateTime beforeDate, @Param("afterDate") LocalDateTime afterDate, Pageable pageable);

    @Query("select a from AssessmentSubmission a where a.assessment.id = ?1 ORDER BY a.submissionDate DESC")
    List<AssessmentSubmission> findByAssessment_Id(Long id);


}
