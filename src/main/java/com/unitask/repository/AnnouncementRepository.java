package com.unitask.repository;

import com.unitask.dto.annoucement.AnnouncementTuple;
import com.unitask.entity.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Query("SELECT a.id as id, " +
            "a.title as title, " +
            "a.subject.name as subjectName, " +
            "a.subject.code as subjectCode, " +
            "a.description as description, " +
            "a.subject.color as color " +
            "FROM Announcement a " +
            "WHERE (:ownerId IS NULL OR a.owner.Id = :ownerId)")
    Page<AnnouncementTuple> findListing(Pageable pageable, Long ownerId);

    @Query("SELECT a.id as id, " +
            "a.title as title, " +
            "a.subject.name as subjectName, " +
            "a.subject.code as subjectCode, " +
            "a.description as description, " +
            "a.subject.color as color " +
            "FROM Announcement a " +
            "LEFT JOIN StudentSubject ss ON ss.subject.id = a.subject.id " +
            "WHERE ss IS NOT NULL")
    Page<AnnouncementTuple> findStudentListing(Pageable pageable);
}
