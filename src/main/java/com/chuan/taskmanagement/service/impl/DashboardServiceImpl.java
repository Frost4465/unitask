package com.chuan.taskmanagement.service.impl;

import com.chuan.taskmanagement.constant.TicketStatus;
import com.chuan.taskmanagement.dao.AppUserDAO;
import com.chuan.taskmanagement.dao.ProjectDAO;
import com.chuan.taskmanagement.dao.TicketDAO;
import com.chuan.taskmanagement.dto.PageRequest;
import com.chuan.taskmanagement.dto.PageResponse;
import com.chuan.taskmanagement.dto.dashboard.DashboardResponse;
import com.chuan.taskmanagement.dto.ticket.TicketResponse;
import com.chuan.taskmanagement.entity.AppUser;
import com.chuan.taskmanagement.entity.Ticket;
import com.chuan.taskmanagement.mapper.TicketMapper;
import com.chuan.taskmanagement.service.ContextService;
import com.chuan.taskmanagement.service.DashboardService;
import com.chuan.taskmanagement.util.PageUtil;
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
