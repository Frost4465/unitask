package com.unitask.dto.dashboard;

import lombok.Data;

@Data
public class DashboardResponse {

    private Long myProjects;
    private Long myTasks;
    private Long toDoTasks;
    private Long inProgressTasks;
    private Long QATasks;
    private Long DoneTasks;
}
