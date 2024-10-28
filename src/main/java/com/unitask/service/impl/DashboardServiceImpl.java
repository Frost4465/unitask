package com.unitask.service.impl;

import com.unitask.constant.TicketStatus;
import com.unitask.dao.AppUserDAO;
import com.unitask.dao.ProjectDAO;
import com.unitask.dao.TicketDAO;
import com.unitask.dto.PageRequest;
import com.unitask.dto.PageResponse;
import com.unitask.dto.dashboard.DashboardResponse;
import com.unitask.dto.ticket.TicketResponse;
import com.unitask.entity.AppUser;
import com.unitask.entity.Ticket;
import com.unitask.mapper.TicketMapper;
import com.unitask.service.ContextService;
import com.unitask.service.DashboardService;
import com.unitask.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl extends ContextService implements DashboardService {

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private AppUserDAO appUserDAO;

    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public DashboardResponse readMyDashboard() {
        AppUser appUser = appUserDAO.findByEmail(getCurrentAuthUsername());
        Long project = projectDAO.countMyProject(appUser.getId());
        Long taskList = ticketDAO.countMyTask(appUser.getId(), null);
        Long toDoTasks = ticketDAO.countMyTask(appUser.getId(), TicketStatus.TO_DO);
        Long inProgressTasks = ticketDAO.countMyTask(appUser.getId(), TicketStatus.IN_PROGRESS);
        Long qaTasks = ticketDAO.countMyTask(appUser.getId(), TicketStatus.QA);
        Long doneTasks = ticketDAO.countMyTask(appUser.getId(), TicketStatus.DONE);


        DashboardResponse dashboardResponse = new DashboardResponse();
        dashboardResponse.setMyProjects(project);
        dashboardResponse.setMyTasks(taskList);
        dashboardResponse.setToDoTasks(toDoTasks);
        dashboardResponse.setInProgressTasks(inProgressTasks);
        dashboardResponse.setQATasks(qaTasks);
        dashboardResponse.setDoneTasks(doneTasks);
        return dashboardResponse;
    }


    @Override
    public PageResponse<Ticket, TicketResponse> readMyTasks(PageRequest pageRequest) {
        AppUser appUser = appUserDAO.findByEmail(getCurrentAuthUsername());
        Pageable pageable = PageUtil.pageable(pageRequest, Sort.by("id").descending());
        Page<Ticket> ticketPage = ticketDAO.findList(pageable, appUser.getId());
        return new PageResponse<>(ticketPage, ticketMapper.toResponse(ticketPage.getContent()));
    }
}
