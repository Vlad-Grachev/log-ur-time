package com.unn.logurtime.service.task;

import com.unn.logurtime.model.Task;
import com.unn.logurtime.model.constants.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface TaskService {
    Task addTask(Task task);
    Task getTask(Long taskId);
    void deleteTask(Task task);
    Task updateTask(Task task);
    Task updateTask(Map<String, Object> newValues);
    List<Task> getCreatedTasksByUsername(String username);
    List<Task> getAssignedTasksByUsername(String username);
    List<Task> getTasksByStatus(TaskStatus status);
    List<Task> getTasksForCreationPeriod(LocalDateTime startDate, LocalDateTime endDate);
    List<Task> getTasksForDuePeriod(LocalDateTime startDate, LocalDateTime endDate);
    List<Task> getOverdueTasks();
    List<Task> getUnassignedTasks();
    //List<TaskPojo> getPagedTask
}
