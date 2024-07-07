package com.chuan.taskmanagement.dto.ticket;

import com.chuan.taskmanagement.constant.TicketStatus;

public interface TicketTuple {
    Long getId();

    Long getCode();

    Long getTitle();

    TicketStatus getStatus();

    Long getStoryPoint();
}
