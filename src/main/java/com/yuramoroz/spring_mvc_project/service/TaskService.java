package com.yuramoroz.spring_mvc_project.service;

import com.yuramoroz.spring_mvc_project.dao.TaskDAO;
import com.yuramoroz.spring_mvc_project.domain.Status;
import com.yuramoroz.spring_mvc_project.domain.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.isNull;

@Service
@Transactional
public class TaskService {

    private final TaskDAO taskDAO;

    public TaskService(TaskDAO taskDAO){
        this.taskDAO = taskDAO;
    }

    public Task getTaskById(int id) {
        return taskDAO.getById(id);
    }

    public List<Task> getAllTasksList(int offset, int limit) {
        return taskDAO.getAllTasks(offset, limit);
    }

    public int getAllCount(){
        return taskDAO.getAllCount();
    }

    @Transactional
    public void createTask(String description, Status status) {
        Task task = new Task();
        task.setStatus(status);
        task.setDescription(description);
        taskDAO.saveOrUpdate(task);
    }

    @Transactional
    public void editTask(int id, String description, Status status){
        Task task = taskDAO.getById(id);
        if(isNull(task)){
            throw new RuntimeException("Not found");
        }

        task.setDescription(description);
        task.setStatus(status);
        taskDAO.saveOrUpdate(task);
    }

    @Transactional
    public void deleteTask(int id) {
        Task task = taskDAO.getById(id);
        if(isNull(task)){
            throw new RuntimeException("Not found");
        }

        taskDAO.delete(task);
    }
}