package com.unitask.dto.ticket;

import com.unitask.constant.Enum.TicketStatus;
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
