package com.chuan.taskmanagement.dao;

import com.chuan.taskmanagement.entity.Ticket;
import com.chuan.taskmanagement.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TicketDAO {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket save(Ticket ticket) {
        if (Objects.isNull(ticket)) {
            return null;
        }
        return ticketRepository.save(ticket);
    }

    public void delete(Ticket ticket) {
        if (Objects.isNull(ticket)) {
            return;
        }
        ticketRepository.delete(ticket);
    }

    public Ticket findById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public List<Ticket> findByProjectId(Long id) {
        return ticketRepository.getTicketList(id);
    }
}
