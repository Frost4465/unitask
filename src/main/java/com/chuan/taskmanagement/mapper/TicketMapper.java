package com.chuan.taskmanagement.mapper;

import com.chuan.taskmanagement.constant.TicketStatus;
import com.chuan.taskmanagement.dto.ticket.TicketBoardResponse;
import com.chuan.taskmanagement.dto.ticket.TicketResponse;
import com.chuan.taskmanagement.dto.ticket.TicketTuple;
import com.chuan.taskmanagement.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TicketMapper {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProjectMapper projectMapper;

    public TicketResponse toResponse(Ticket ticket) {
        if (Objects.isNull(ticket)) {
            return null;
        }

        TicketResponse response = new TicketResponse();
        response.setId(ticket.getId());
        response.setCode(ticket.getProject().getCode() + "-" + ticket.getId());
        response.setTitle(ticket.getTitle());
        response.setDescription(ticket.getDescription());
        response.setStatus(ticket.getStatus());
        response.setStoryPoints(ticket.getStoryPoints());
        response.setAuthor(userMapper.toDropdown(ticket.getAuthor()));
        response.setAssignedPerson(userMapper.toDropdown(ticket.getAssignedPerson()));
        response.setProject(projectMapper.toDropdown(ticket.getProject()));
        return response;
    }

    public List<TicketBoardResponse> toResponse(List<TicketTuple> ticketTuples) {
        TicketBoardResponse TO_DO = new TicketBoardResponse(TicketStatus.TO_DO, new ArrayList<>());
        TicketBoardResponse IN_PROGRESS = new TicketBoardResponse(TicketStatus.IN_PROGRESS, new ArrayList<>());
        TicketBoardResponse QA = new TicketBoardResponse(TicketStatus.QA, new ArrayList<>());
        TicketBoardResponse DONE = new TicketBoardResponse(TicketStatus.DONE, new ArrayList<>());

        for (TicketTuple tuple : ticketTuples) {
            switch (tuple.getStatus()) {
                case TO_DO -> {
                    TO_DO.getTickets().add(tuple);
                }
                case IN_PROGRESS -> {
                    IN_PROGRESS.getTickets().add(tuple);
                }
                case QA -> {
                    QA.getTickets().add(tuple);
                }
                case DONE -> {
                    DONE.getTickets().add(tuple);
                }
            }
        }

        return List.of(TO_DO, IN_PROGRESS, QA, DONE);
    }
}
