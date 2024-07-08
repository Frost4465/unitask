package com.chuan.taskmanagement.repository;

import com.chuan.taskmanagement.dto.ticket.TicketTuple;
import com.chuan.taskmanagement.entity.Ticket;
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
            "t.storyPoints as storyPoint " +
            "FROM Ticket t " +
            "LEFT JOIN t.assignedPerson ap " +
            "WHERE t.project.id = :id ")
    List<TicketTuple> getTicketList(@Param("id") Long id);
}
