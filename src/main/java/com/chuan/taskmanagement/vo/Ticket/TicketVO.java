package com.chuan.taskmanagement.vo.Ticket;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TicketVO {

    private Long id;
    private String title;
    private String description;
    private String status;
    private String storyPoints;
    private String author;
    private String assignedPerson;

}
