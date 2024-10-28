package com.unitask.service;

import com.unitask.dto.ticket.TicketBoardResponse;
import com.unitask.dto.ticket.TicketRequest;
import com.unitask.dto.ticket.TicketResponse;

import java.util.List;


public interface TickerService {

    void createTicket(TicketRequest ticketRequest);

    void updateTicket(Long id, TicketRequest ticketRequest);

    void updateTicketStatus(Long id, TicketRequest ticketRequest);

    TicketResponse getTicket(Long ticketId);

    void deleteTicket(Long ticketId);

    List<TicketBoardResponse> projectTicketList(Long id);
}
