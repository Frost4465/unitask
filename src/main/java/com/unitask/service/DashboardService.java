package com.unitask.service;

import com.unitask.dto.PageRequest;
import com.unitask.dto.PageResponse;
import com.unitask.dto.dashboard.DashboardResponse;
import com.unitask.dto.ticket.TicketResponse;
import com.unitask.entity.Ticket;

public interface DashboardService {

    DashboardResponse readMyDashboard();

    PageResponse<Ticket, TicketResponse> readMyTasks(PageRequest pageRequest);
}
