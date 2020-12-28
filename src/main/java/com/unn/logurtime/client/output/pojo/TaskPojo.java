package com.unn.logurtime.client.output.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskPojo {
    private Long id;
    private String name;
    private String description;
    private String creator;
    private String assignee;
    private String creationDate;
    private String dueDate;
    private String formattedCapacity;
    private String priority;
    private String status;

    public TaskPojo() {
    }
}
