package com.chuan.taskmanagement.dto.ticket;

import com.chuan.taskmanagement.constant.TicketStatus;
import com.chuan.taskmanagement.dto.DropdownResponse;
import lombok.Data;

@Data
public class TicketResponse {

    private Long id;
    private String code;
    private String title;
    private String description;
    private TicketStatus status;
    private Integer storyPoints;
    private DropdownResponse author;
    private DropdownResponse assignedPerson;
    private DropdownResponse project;
}
