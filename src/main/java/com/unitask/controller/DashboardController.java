package com.unitask.controller;

import com.unitask.dto.PageRequest;
import com.unitask.dto.PageResponse;
import com.unitask.dto.dashboard.DashboardResponse;
import com.unitask.dto.ticket.TicketResponse;
import com.unitask.entity.Ticket;
import com.unitask.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(path = "dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("")
    public ResponseEntity<DashboardResponse> myDashboard() {
        return ResponseEntity.ok(dashboardService.readMyDashboard());
    }

    @GetMapping("my-tasks")
    public ResponseEntity<PageResponse<Ticket, TicketResponse>> myTasks(PageRequest pageRequest) {
        return ResponseEntity.ok(dashboardService.readMyTasks(pageRequest));
    }
}
