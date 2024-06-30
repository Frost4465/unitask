package com.chuan.taskmanagement.service.impl;

import com.chuan.taskmanagement.dao.AppUserDAO;
import com.chuan.taskmanagement.dao.ProjectDAO;
import com.chuan.taskmanagement.dao.TicketDAO;
import com.chuan.taskmanagement.dto.ticket.CreateTicketRequest;
import com.chuan.taskmanagement.dto.ticket.UpdateTicketRequest;
import com.chuan.taskmanagement.entity.AppUser;
import com.chuan.taskmanagement.entity.Ticket;
import com.chuan.taskmanagement.exception.ServiceAppException;
import com.chuan.taskmanagement.service.BaseService;
import com.chuan.taskmanagement.service.TickerService;
import com.chuan.taskmanagement.vo.ticket.TicketVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TickerService {

    @Autowired
    private BaseService baseService;
    @Autowired
    private AppUserDAO appUserDAO;
    @Autowired
    private TicketDAO ticketDAO;
    @Autowired
    private ProjectDAO projectDAO;

    @Override
    public void createTicket(CreateTicketRequest ticketRequest) {
        AppUser assignedAppUser = appUserDAO.findByEmail(ticketRequest.getAssigned());
        AppUser authorAppUser = appUserDAO.findByEmail(baseService.getCurrentAuthUsername());
        Ticket ticket = Ticket.builder()
                .title(ticketRequest.getTitle())
                .description(ticketRequest.getDescription())
                .status(ticketRequest.getStatus())
                .storyPoints(ticketRequest.getStoryPoints())
                .author(authorAppUser)
                .assignedPerson(assignedAppUser)
                .build();
        ticketDAO.save(ticket);
    }

    @Override
    public void updateTicket(UpdateTicketRequest ticketRequest) {
        Ticket ticket = ticketDAO.findById(ticketRequest.getTicketId());
        AppUser assignedAppUser = appUserDAO.findByEmail(ticketRequest.getAssigned());
        if (ticket == null) {
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Ticket Does Not Exists");
        }
        ticket.setTitle(ticket.getTitle());
        ticket.setDescription(ticketRequest.getDescription());
        ticket.setStatus(ticket.getStatus());
        ticket.setStoryPoints(ticketRequest.getStoryPoints());
        if (assignedAppUser != null) {
            ticket.setAssignedPerson(assignedAppUser);
        }
        ticketDAO.save(ticket);
    }

    @Override
    public TicketVO getTicket(Long ticketId) {
        Ticket ticket = ticketDAO.findById(ticketId);
        return TicketVO.builder()
                .id(ticket.getId())
                .title(ticket.getTitle())
                .description(ticket.getDescription())
                .status(ticket.getStatus())
                .author(ticket.getAuthor() != null ? ticket.getAuthor().getEmail() : null)
                .assignedPerson(ticket.getAssignedPerson() != null ? ticket.getAssignedPerson().getEmail() : null)
                .build();
    }

    @Override
    public void deleteTicket(Long ticketId) {
        Ticket ticket = ticketDAO.findById(ticketId);
        ticket.setDeleted(true);
        ticketDAO.save(ticket);
    }

    @Override
    public List<TicketVO> projectTicketList(Long id) {
        List<Ticket> ticketList = ticketDAO.findByProjectId(id);
        return ticketList.stream().map(ticket -> {
            return TicketVO.builder()
                    .id(ticket.getId())
                    .title(ticket.getTitle())
                    .description(ticket.getDescription())
                    .status(ticket.getStatus())
                    .author(ticket.getAuthor() != null ? ticket.getAuthor().getEmail() : null)
                    .assignedPerson(ticket.getAssignedPerson() != null ? ticket.getAssignedPerson().getEmail() : null)
                    .build();
        }).collect(Collectors.toList());
    }


}
