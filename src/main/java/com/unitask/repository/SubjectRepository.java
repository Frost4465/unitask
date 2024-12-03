package com.unitask.repository;

import com.unitask.dto.studentSubject.StudentSubjectTuple;
import com.unitask.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query("SELECT " +
            "s.Id as id, " +
            "s.name as name, " +
            "s.code as code," +
            "s.lecturerName as lecturerName," +
            "ss.status as status " +
            "FROM Subject s " +
            "LEFT JOIN StudentSubject ss ON ss.subject.Id = s.Id " +
            "LEFT JOIN ss.user ssu " +
            "WHERE (ss IS NULL OR ssu.email = :email) " +
            "ORDER BY ss.status DESC "
    )
    List<StudentSubjectTuple> findByStudentEmail(String email);
}
