package com.unitask.repository;

import com.unitask.constant.TicketStatus;
import com.unitask.dto.ticket.TicketTuple;
import com.unitask.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t.id as id," +
            "t.project.name as code, " +
            "t.title as title, " +
            "t.status as status, " +
            "ap.name as assignee, " +
            "ap.Id as assigneeId, " +
            "t.storyPoints as storyPoint " +
            "FROM Ticket t " +
            "LEFT JOIN t.assignedPerson ap " +
            "WHERE (:id IS NULL OR t.project.id = :id) ")
    List<TicketTuple> getTicketList(@Param("id") Long id);

    @Query("SELECT t " +
            "FROM Ticket t " +
            "LEFT JOIN t.assignedPerson ap " +
            "WHERE (:userId IS NULL or t.assignedPerson.Id = :userId) ")
    Page<Ticket> findTicketList(Pageable pageable, @Param("userId") Long userId);

    @Query("select count(t) from Ticket t where t.assignedPerson.Id = :Id and (:status IS NULL OR t.status = :status)")
    long countByAuthor_IdAndStatus(@Param("Id") Long Id, @Param("status") TicketStatus status);

}
