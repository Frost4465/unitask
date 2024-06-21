package com.chuan.taskmanagement.dao;

import com.chuan.taskmanagement.entity.Ticket;
import com.chuan.taskmanagement.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketDAO {

    @Autowired
    private TicketRepository ticketRepository;

    public void save(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    public Ticket findById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public List<Ticket> findAll() {
        return ticketRepository.getTicketList();
    }
}
