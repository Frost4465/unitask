package com.unitask.dao;

import com.unitask.entity.Task;
import com.unitask.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskDao {

    @Autowired
    private TaskRepository taskRepository;

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public List<Task> findByUserId(Long id) {
        return taskRepository.findByUser_Id(id);
    }

    public List<Task> findByAssessmentsAndGroupId(List<Long> groupIds, List<Long> assessmentIds) {
        return taskRepository.findByAssessment_StudentAssessments_Group_IdInAndAssessment_IdIn(groupIds, assessmentIds);
    }

}
