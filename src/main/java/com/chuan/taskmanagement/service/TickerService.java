package com.chuan.taskmanagement.service;

import com.chuan.taskmanagement.dto.ticket.TicketBoardResponse;
import com.chuan.taskmanagement.dto.ticket.TicketRequest;
import com.chuan.taskmanagement.dto.ticket.TicketResponse;

import java.util.List;


public interface TickerService {

    void createTicket(TicketRequest ticketRequest);

    void updateTicket(Long id, TicketRequest ticketRequest);

    TicketResponse getTicket(Long ticketId);

    void deleteTicket(Long ticketId);

    List<TicketBoardResponse> projectTicketList(Long id);
}
