package com.unitask.repository;

import com.unitask.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {


    @Query(value = "select g from Group g " +
            "where (?1 is null or g.name like ?1)")
    Page<Group> findByGroupName(String groupName, Pageable pageable);

}
