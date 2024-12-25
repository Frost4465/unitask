package com.unitask.repository;

import com.unitask.dto.subject.SubjectTuple;
import com.unitask.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("SELECT " +
            "s.id as id, " +
            "s.name as name, " +
            "s.code as code," +
            "s.color as color," +
            "s.lecturerName as lecturerName " +
            "FROM Subject s " +
            "WHERE  (:search IS NULL OR s.name LIKE %:search%) " +
            "AND (:email IS NULL OR s.owner.email = :email) " +
            "AND (:subjectId IS NULL OR s.id = :subjectId) ")
    Page<SubjectTuple> findListing(Pageable pageable, String email, String search, Long subjectId);

}
