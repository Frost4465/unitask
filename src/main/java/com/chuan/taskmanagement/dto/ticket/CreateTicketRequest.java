package com.chuan.taskmanagement.dto.ticket;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NonNull;

@Data
public class CreateTicketRequest {


    @NonNull
    private String title;
    @NonNull
    private String description;
    private String status;
    private Integer storyPoints;
    @Email
    private String assigned;

}
