package com.chuan.taskmanagement.service;

import com.chuan.taskmanagement.dto.PageRequest;
import com.chuan.taskmanagement.dto.PageResponse;
import com.chuan.taskmanagement.dto.dashboard.DashboardResponse;
import com.chuan.taskmanagement.dto.ticket.TicketResponse;
import com.chuan.taskmanagement.entity.Ticket;
import org.springframework.data.web.PagedModel;

public interface DashboardService {

    DashboardResponse readMyDashboard();

    PageResponse<Ticket, TicketResponse> readMyTasks(PageRequest pageRequest);
}
