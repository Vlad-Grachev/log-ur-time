package com.unn.logurtime.client.output.transformer;


import com.unn.logurtime.client.output.Transformer;
import com.unn.logurtime.client.output.pojo.TaskPojo;
import com.unn.logurtime.model.Task;
import com.unn.logurtime.model.TimeReport;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Setter
@Getter
public class TaskTransformer extends Transformer<Task, TaskPojo> {
    @Override
    public TaskPojo transform(Task task) {
        TaskPojo taskPojo = new TaskPojo();
        taskPojo.setId(task.getId());
        taskPojo.setName(task.getName());
        taskPojo.setDescription(task.getDescription());
        taskPojo.setCreator(task.getCreator().getUsername());
        taskPojo.setAssignee(task.getAssignee() != null ? task.getAssignee().getUsername() : null);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        taskPojo.setCreationDate(formatter.format(task.getCreationDate()));
        taskPojo.setDueDate(formatter.format(task.getDueDate()));
        taskPojo.setFormattedCapacity(getFormattedCapacity(task.getEstimation(),
                calculateSpentTime(task.getTimeReports())));
        taskPojo.setPriority(task.getPriority());
        taskPojo.setStatus(task.getStatus());

        return taskPojo;
    }

    private String getFormattedCapacity(int estimated, int spentTime) {
        return formatTime(estimated) + "/" + formatTime(spentTime);
    }

    private String formatTime(int minutes) {

        return minutes / 60 + "h" + (minutes % 60 != 0 ? " " + minutes % 60 + "m" : "");
    }

    private int calculateSpentTime(List<TimeReport> timeReports) {
        int result = 0;
        if (!CollectionUtils.isEmpty(timeReports)) {
            result = timeReports.stream().mapToInt(TimeReport::getSpentTime).sum();
        }
        return result;
    }

    public TaskTransformer() {
    }
}
