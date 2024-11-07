package com.unitask.dto.ticket;

import com.unitask.constant.Enum.TicketStatus;

public interface TicketTuple {
    Long getId();

    String getCode();

    String getTitle();

    TicketStatus getStatus();

    Integer getStoryPoint();

    String getAssignee();

    Long getAssigneeId();
}
