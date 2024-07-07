package com.chuan.taskmanagement.service.impl;

import com.chuan.taskmanagement.dao.AppUserDAO;
import com.chuan.taskmanagement.dao.ProjectDAO;
import com.chuan.taskmanagement.dao.TicketDAO;
import com.chuan.taskmanagement.dto.ticket.TicketBoardResponse;
import com.chuan.taskmanagement.dto.ticket.TicketRequest;
import com.chuan.taskmanagement.dto.ticket.TicketResponse;
import com.chuan.taskmanagement.dto.ticket.TicketTuple;
import com.chuan.taskmanagement.entity.AppUser;
import com.chuan.taskmanagement.entity.Project;
import com.chuan.taskmanagement.entity.Ticket;
import com.chuan.taskmanagement.mapper.TicketMapper;
import com.chuan.taskmanagement.service.ContextService;
import com.chuan.taskmanagement.service.TickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl extends ContextService implements TickerService {

    @Autowired
    private AppUserDAO appUserDAO;
    @Autowired
    private TicketDAO ticketDAO;
    @Autowired
    private ProjectDAO projectDAO;
    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public void createTicket(TicketRequest ticketRequest) {
        AppUser assignedAppUser = appUserDAO.findById(ticketRequest.getAssignedId());
        AppUser authorAppUser = appUserDAO.findByEmail(getCurrentAuthUsername());
        Project project = projectDAO.findById(ticketRequest.getProjectId());
        Ticket ticket = new Ticket();
        ticket.setTitle(ticketRequest.getTitle());
        ticket.setDescription(ticketRequest.getDescription());
        ticket.setStatus(ticketRequest.getStatus());
        ticket.setStoryPoints(ticketRequest.getStoryPoints());
        ticket.setAuthor(authorAppUser);
        ticket.setAssignedPerson(assignedAppUser);
        ticket.setProject(project);
        ticketDAO.save(ticket);
    }

    @Override
    public void updateTicket(Long id, TicketRequest ticketRequest) {
        AppUser assignedAppUser = appUserDAO.findById(ticketRequest.getAssignedId());
        Ticket ticket = ticketDAO.findById(id);
        ticket.setTitle(ticketRequest.getTitle());
        ticket.setDescription(ticketRequest.getDescription());
        ticket.setStatus(ticketRequest.getStatus());
        ticket.setStoryPoints(ticketRequest.getStoryPoints());
        ticket.setAssignedPerson(assignedAppUser);
        ticketDAO.save(ticket);
    }

    @Override
    public TicketResponse getTicket(Long ticketId) {
        Ticket ticket = ticketDAO.findById(ticketId);
        return ticketMapper.toResponse(ticket);
    }

    @Override
    public void deleteTicket(Long ticketId) {
        Ticket ticket = ticketDAO.findById(ticketId);
        ticketDAO.delete(ticket);
    }

    @Override
    public List<TicketBoardResponse> projectTicketList(Long id) {
        List<TicketTuple> ticketList = ticketDAO.findByProjectId(id);
        return ticketMapper.toResponse(ticketList);
    }
}
