package com.chuan.taskmanagement.repository;

import com.chuan.taskmanagement.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM ticket t WHERE t.is_deleted = 0 && t.project.id = :id ")
    List<Ticket> getTicketList(Long id);
}
