package com.unitask.repository;

import com.unitask.dto.assessment.AssessmentSubmissionTuple;
import com.unitask.entity.assessment.AssessmentSubmission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
            "where (sub.owner.id = :ownerId) " +
            "order by asub.submissionDate DESC")
    Page<AssessmentSubmissionTuple> getAssessmentSubmissionListing(Long ownerId, Pageable pageable);
}
