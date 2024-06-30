package com.chuan.taskmanagement.service;

import com.chuan.taskmanagement.dto.ticket.CreateTicketRequest;
import com.chuan.taskmanagement.dto.ticket.UpdateTicketRequest;
import com.chuan.taskmanagement.vo.ticket.TicketVO;

import java.util.List;


public interface TickerService {

    void createTicket(CreateTicketRequest ticketRequest);

    void updateTicket(UpdateTicketRequest ticketRequest);

    TicketVO getTicket(Long ticketId);

    void deleteTicket(Long ticketId);

    List<TicketVO> projectTicketList(Long id);
}
