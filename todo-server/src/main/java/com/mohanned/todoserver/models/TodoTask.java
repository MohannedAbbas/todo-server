package com.mohanned.todoserver.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class TodoTask {

    @EmbeddedId
    private TaskId taskId;

    private String description;

    public TodoTask() {
    }

    public TodoTask(TaskId taskId, String description) {
        this.taskId = taskId;
        this.description = description;
    }

    public TaskId getTaskId() {
        return taskId;
    }

    public void setTaskId(TaskId taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
