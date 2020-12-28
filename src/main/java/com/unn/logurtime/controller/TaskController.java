package com.unn.logurtime.controller;

import com.unn.logurtime.client.output.TransformedPageRecords;
import com.unn.logurtime.client.output.pojo.TaskPojo;
import com.unn.logurtime.client.output.transformer.TaskTransformer;
import com.unn.logurtime.model.Task;
import com.unn.logurtime.model.constants.Constants;
import com.unn.logurtime.model.constants.TaskStatus;
import com.unn.logurtime.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    public Task newTask(@RequestBody Task task) {
        return taskService.addTask(task);
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        Task task = taskService.getTask(id);
        return task;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable Long id) {
        Task task = taskService.getTask(id);
        if (task != null) {
            taskService.deleteTask(task);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping()
    public Task updateTask(@RequestBody Task task) {
        return taskService.updateTask(task);
    }

    @PutMapping("/update")
    public ResponseEntity<Task> updateTask(@RequestBody Map<String, Object> values){
        if (!CollectionUtils.isEmpty(values) && values.containsKey(Constants.Common.Parameters.ID)) {
            return new ResponseEntity<>(taskService.updateTask(values), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/created/{username}")
    public List<Task> getCreatedTasksByUsername(@PathVariable String username) {
        return taskService.getCreatedTasksByUsername(username);
    }

    @GetMapping("/assigned/{username}")
    public List<Task> getAssignedTasksByUsername(@PathVariable String username) {
        return taskService.getAssignedTasksByUsername(username);
    }

    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable TaskStatus status) {
        return taskService.getTasksByStatus(status);
    }

    @PostMapping("/created")
    public List<Task> getTasksForCreationPeriod(@RequestBody LocalDateTime startDate,
                                                @RequestBody LocalDateTime endDate) {
        return taskService.getTasksForCreationPeriod(startDate, endDate);
    }

    @PostMapping("/due")
    public List<Task> getTasksForDuePeriod(@RequestBody LocalDateTime startDate,
                                           @RequestBody LocalDateTime endDate) {
        return taskService.getTasksForDuePeriod(startDate, endDate);
    }

    @GetMapping("/overdue")
    public List<Task> getOverdueTasks() {
        return taskService.getOverdueTasks();
    }

    @GetMapping("/unassigned")
    public List<Task> getUnassignedTasks() {
        return taskService.getUnassignedTasks();
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/getmine")
    TransformedPageRecords<Task,TaskPojo> getAuthUserRequests(@RequestParam int page, @RequestParam int limit) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Task> assignedTasks = taskService.getAssignedTasksByUsername(username);
        return !CollectionUtils.isEmpty(assignedTasks) ?
                new TransformedPageRecords<>(assignedTasks, page, limit, new TaskTransformer())
                : null;
    }
}
