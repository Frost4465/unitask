package com.chuan.taskmanagement.dto.ticket;

import com.chuan.taskmanagement.constant.TicketStatus;
import lombok.Data;

import java.util.List;

@Data
public class TicketBoardResponse {
    private TicketStatus status;
    private List<TicketResponse> tickets;

    public TicketBoardResponse(TicketStatus status, List<TicketResponse> ticketTuples) {
        this.status = status;
        this.tickets = ticketTuples;
    }
}
