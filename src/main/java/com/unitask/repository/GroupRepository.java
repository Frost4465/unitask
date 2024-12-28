package com.unitask.repository;

import com.unitask.dto.group.GroupTuple;
import com.unitask.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {


    @Query("select g.id as id," +
            "g.assessment.subject.code as subjectCode, " +
            "g.assessment.name as assignmentName," +
            "g.assessment.maxMember as maxMember," +
            "g.name as groupName," +
            "(SELECT COUNT(sa) FROM g.studentAssessment sa WHERE sa.group.id = g.id ) as memberCount " +
            "from Group g where (:name is null or g.name like %:name%)")
    Page<GroupTuple> findByName(String name, Pageable pageable);

}
