package com.mohanned.todoserver.restapi.webmodels;

public class TodoListBody {

    private String name;

    public TodoListBody() {
    }

    public TodoListBody(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
