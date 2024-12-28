package com.unitask.repository;

import com.unitask.dto.assessment.AssessmentSubmissionTuple;
import com.unitask.entity.assessment.AssessmentSubmission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

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
}
