package com.chuan.taskmanagement.dto.ticket;

import com.chuan.taskmanagement.constant.TicketStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class TicketRequest {

    @NotNull
    private String title;
    @NotNull
    private String description;
    private TicketStatus status;
    private Integer storyPoints;
    private Long projectId;
    private Long assignedId;

}
