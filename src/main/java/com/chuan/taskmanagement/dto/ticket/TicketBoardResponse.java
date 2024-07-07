package com.chuan.taskmanagement.dto.ticket;

import com.chuan.taskmanagement.constant.TicketStatus;
import lombok.Data;

import java.util.List;

@Data
public class TicketBoardResponse {
    private TicketStatus status;
    private List<TicketTuple> tickets;

    public TicketBoardResponse(TicketStatus status, List<TicketTuple> ticketTuples) {
        this.status = status;
        this.tickets = ticketTuples;
    }
}
