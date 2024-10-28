package com.unitask.service.impl;

import com.unitask.dao.AppUserDAO;
import com.unitask.dao.ProjectDAO;
import com.unitask.dao.TicketDAO;
import com.unitask.dto.ticket.TicketBoardResponse;
import com.unitask.dto.ticket.TicketRequest;
import com.unitask.dto.ticket.TicketResponse;
import com.unitask.dto.ticket.TicketTuple;
import com.unitask.entity.AppUser;
import com.unitask.entity.Project;
import com.unitask.entity.Ticket;
import com.unitask.mapper.TicketMapper;
import com.unitask.service.ContextService;
import com.unitask.service.TickerService;
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
    public void updateTicketStatus(Long id, TicketRequest ticketRequest) {
        Ticket ticket = ticketDAO.findById(id);
        ticket.setStatus(ticketRequest.getStatus());
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
        List<TicketTuple> ticketList = ticketDAO.findList(id);
        return ticketMapper.toResponse(ticketList);
    }
}
