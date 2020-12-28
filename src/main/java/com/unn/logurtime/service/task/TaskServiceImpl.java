package com.unn.logurtime.service.task;

import com.unn.logurtime.model.Task;
import com.unn.logurtime.model.User;
import com.unn.logurtime.model.constants.Constants;
import com.unn.logurtime.model.constants.TaskStatus;
import com.unn.logurtime.repository.TaskRepository;
import com.unn.logurtime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Task addTask(Task task) {
        return taskRepository.saveAndFlush(task);
    }

    @Override
    public Task getTask(Long taskId) {
        return taskRepository.getOne(taskId);
    }

    @Override
    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }

    @Override
    public Task updateTask(Task task) {
        return taskRepository.saveAndFlush(task);
    }

    @Override
    public Task updateTask(Map<String, Object> newValues) {
        Long taskId = getTaskId(newValues.get(Constants.Common.Parameters.ID));
        Task task = taskId != null ?
                taskRepository.getOne(taskId)
                : null;
        if (task != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            for (Map.Entry<String, Object> entry : newValues.entrySet()) {
                switch (entry.getKey()) {
                    case Constants.Common.Parameters.NAME:
                        task.setName((String) entry.getValue());
                        break;
                    case Constants.Task.Parameters.Description:
                        task.setDescription((String) entry.getValue());
                        break;
                    case Constants.Task.Parameters.CREATION_DATE:
                        task.setDueDate(LocalDateTime.parse((String) entry.getValue(), formatter));
                        break;
                    case Constants.Task.Parameters.DUE_DATE:
                        task.setDueDate(LocalDateTime.parse((String) entry.getValue(), formatter));
                        break;
                    case Constants.Task.Parameters.ESTIMATION:
                        task.setEstimation((Integer) entry.getValue());
                        break;
                    case Constants.Task.Parameters.CREATOR:
                        task.setCreator(userRepository.findByUsername((String) entry.getValue()));
                        break;
                    case Constants.Task.Parameters.ASSIGNEE:
                        task.setAssignee(userRepository.findByUsername((String) entry.getValue()));
                        break;
                    case Constants.Task.Parameters.STATUS:
                        task.setStatus((String) entry.getValue());
                        break;
                    case Constants.Task.Parameters.PRIORITY:
                        task.setPriority((String) entry.getValue());
                        break;
                    case Constants.Task.Parameters.MOVE_DD_COUNTER:
                        task.setMoveDdCounter((Integer) entry.getValue());
                        break;
                    default:
                        ;
                }
            }
        }
        return taskRepository.saveAndFlush(task);
    }

    private Long getTaskId(Object o) {
        try {
            if (o instanceof Integer) {
                return new Long((Integer) o);
            } else if (o instanceof String) {
                return new Long((String) o);
            }
        } catch (ClassCastException e) {
            return null;
        }
        return null;
    }

    @Override
    public List<Task> getCreatedTasksByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user != null ? taskRepository.findByCreator(user) : null;
    }

    @Override
    public List<Task> getAssignedTasksByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user != null ? taskRepository.findByAssignee(user) : null;
    }

    @Override
    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status.toString());
    }

    @Override
    public List<Task> getTasksForCreationPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        return taskRepository.findByCreationDateBetween(startDate, endDate);
    }

    @Override
    public List<Task> getTasksForDuePeriod(LocalDateTime startDate, LocalDateTime endDate) {
        return taskRepository.findByDueDateBetween(startDate, endDate);
    }

    @Override
    public List<Task> getOverdueTasks() {
        return taskRepository.findByDueDateBefore(LocalDateTime.now());
    }

    @Override
    public List<Task> getUnassignedTasks() {
        return taskRepository.findByAssigneeIsNull();
    }
}
