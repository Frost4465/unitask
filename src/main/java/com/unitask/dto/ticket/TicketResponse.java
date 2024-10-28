package com.unitask.dto.ticket;

import com.unitask.constant.TicketStatus;
import com.unitask.dto.DropdownResponse;
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
