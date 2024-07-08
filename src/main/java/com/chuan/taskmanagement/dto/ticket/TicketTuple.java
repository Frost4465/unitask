package com.chuan.taskmanagement.dto.ticket;

import com.chuan.taskmanagement.constant.TicketStatus;

public interface TicketTuple {
    Long getId();

    String getCode();

    String getTitle();

    TicketStatus getStatus();

    Integer getStoryPoint();

    String getAssignee();

    Long getAssigneeId();
}
