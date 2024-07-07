package com.chuan.taskmanagement.dto.ticket;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class CreateTicketRequest {

    @NotNull
    private String title;
    @NotNull
    private String description;
    private String status;
    private Integer storyPoints;
    private Long projectId;
    @Email
    private Long assignedId;

}
