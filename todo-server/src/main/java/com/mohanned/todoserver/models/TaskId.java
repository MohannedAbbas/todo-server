package com.mohanned.todoserver.models;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TaskId implements Serializable {

    private String taskId;
    private String listId;

    public TaskId() {
    }

    public TaskId(String listId, String taskId) {
        this.taskId = taskId;
        this.listId = listId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }
}
