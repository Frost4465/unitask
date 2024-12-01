package com.unitask.repository;

import com.unitask.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {


    @Query("select a from Assessment a where a.subject.id = ?1")
    List<Assessment> findBySubject_Id(Long Id);
}
