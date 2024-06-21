package com.chuan.taskmanagement.service;

import com.chuan.taskmanagement.dto.CreateTicketRequest;
import com.chuan.taskmanagement.dto.UpdateTicketRequest;
import com.chuan.taskmanagement.vo.Ticket.TicketVO;

import java.util.List;


public interface TickerService {

    void createTicket(CreateTicketRequest ticketRequest);

    void updateTicket(UpdateTicketRequest ticketRequest);

    TicketVO getTicket(Long ticketId);

    void deleteTicket(Long ticketId);

    List<TicketVO> ticketList();
}
