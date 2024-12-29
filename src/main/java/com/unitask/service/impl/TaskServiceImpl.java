package com.unitask.service.impl;

import com.unitask.dao.AppUserDAO;
import com.unitask.dao.AssessmentDao;
import com.unitask.dao.GroupDao;
import com.unitask.dao.TaskDao;
import com.unitask.dto.task.TaskRequest;
import com.unitask.dto.task.TaskResponse;
import com.unitask.entity.Group;
import com.unitask.entity.Task;
import com.unitask.entity.User.AppUser;
import com.unitask.entity.assessment.Assessment;
import com.unitask.exception.ServiceAppException;
import com.unitask.mapper.TaskMapper;
import com.unitask.repository.TaskRepository;
import com.unitask.service.ContextService;
import com.unitask.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl extends ContextService implements TaskService {

    @Autowired
    private TaskDao taskDao;
    @Autowired
    private AssessmentDao assessmentDao;
    @Autowired
    private AppUserDAO appUserDAO;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void createTask(TaskRequest taskRequest) {
        Assessment assessment = null;
        if (taskRequest.getAssigmentId() != null){
            assessment = assessmentDao.findById(taskRequest.getAssigmentId());
            if (assessment == null){
                throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Assessment does not Exists");
            }
        }
        AppUser appUser = appUserDAO.findByEmail(getCurrentAuthUsername());

        Task task = new Task();
        task.setAssessment(assessment);
        task.setName(taskRequest.getName());
        task.setUser(appUser);
        task.setChecked(Boolean.FALSE);
        taskDao.save(task);
    }

    @Override
    public void updateTask(Long id, TaskRequest taskRequest) {
        Task task = taskDao.findById(id);
        if (task == null){
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Task does not Exists");
        }
        Assessment assessment = null;
        if (taskRequest.getAssigmentId() != null){
            assessment = assessmentDao.findById(taskRequest.getAssigmentId());
            if (assessment == null){
                throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Assessment does not Exists");
            }
        }
        task.setName(taskRequest.getName());
        task.setAssessment(assessment);
        task.setChecked(taskRequest.getChecked());
        taskDao.save(task);
    }

    @Override
    public void checkTask(Long id) {
        Task task = taskDao.findById(id);
        if (task == null){
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Task does not Exists");
        }
        task.setChecked(Boolean.TRUE);
        taskRepository.save(task);
    }

    @Override
    public TaskResponse getTask(Long id) {
        Task task = taskDao.findById(id);
        if (task == null){
            throw new ServiceAppException(HttpStatus.BAD_REQUEST, "Task does not Exists");
        }
        return TaskMapper.INSTANCE.entityToResponse(task);
    }

    @Override
    public List<TaskResponse> getTaskList() {
        AppUser appUser = appUserDAO.findByEmail(getCurrentAuthUsername());
        List<Task> taskList = taskDao.findByUserId(appUser.getId());
        return TaskMapper.INSTANCE.entityListToResponseList(taskList);
    }

    @Override
    public List<TaskResponse> getGroupTask() {
        AppUser appUser = appUserDAO.findByEmail(getCurrentAuthUsername());
        List<Group> groupList = groupDao.findByUserId(appUser.getId());
        List<Long> assessments = groupList.stream().map(Group::getAssessment).map(Assessment::getId).distinct().toList();
        List<Task> taskList = taskDao.findByAssessments(assessments);
        return TaskMapper.INSTANCE.entityListToResponseList(taskList);
    }


}
