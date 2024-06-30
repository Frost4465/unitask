package com.chuan.taskmanagement.dto.ticket;

import lombok.Data;
import lombok.NonNull;

@Data
public class UpdateTicketRequest {

    @NonNull
    private Long ticketId;
    @NonNull
    private String title;
    @NonNull
    private String description;
    private String status;
    private Integer storyPoints;
    private String assigned;

}
