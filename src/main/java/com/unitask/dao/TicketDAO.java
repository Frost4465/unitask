package com.unitask.dao;

import com.unitask.constant.TicketErrorConstant;
import com.unitask.constant.TicketStatus;
import com.unitask.dto.ticket.TicketTuple;
import com.unitask.entity.Ticket;
import com.unitask.exception.ServiceAppException;
import com.unitask.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
        return ticketRepository.findById(id).orElseThrow(() -> new ServiceAppException(HttpStatus.BAD_REQUEST, TicketErrorConstant.NOT_FOUND));
    }

    public List<TicketTuple> findList(Long id) {
        return ticketRepository.getTicketList(id);
    }

    public Page<Ticket> findList(Pageable pageable, Long userId) {
        return ticketRepository.findTicketList(pageable, userId);
    }

    public Long countMyTask(Long userId, TicketStatus status) {
        return ticketRepository.countByAuthor_IdAndStatus(userId, status);
    }
}
