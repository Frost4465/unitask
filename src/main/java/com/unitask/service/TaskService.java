package com.unitask.service;

import com.unitask.dto.task.TaskRequest;
import com.unitask.dto.task.TaskResponse;

import java.util.List;

public interface TaskService {

    void createTask(TaskRequest taskRequest);

    void updateTask(Long id, TaskRequest taskRequest);

    void checkTask(Long id);

    TaskResponse getTask(Long id);

    List<TaskResponse> getTaskList();

    List<TaskResponse> getGroupTask();

}
