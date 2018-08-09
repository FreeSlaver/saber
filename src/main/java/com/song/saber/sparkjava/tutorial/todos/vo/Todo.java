package com.sparkjava.tutorial.todos.vo;

import java.util.Map;

/**
 * Created by 00013708 on 2017/11/11.
 */
public class Todo {

    private int id;

    private String title;

    private String status;

    public Todo(Map<Object, Object> entry) {
        this.id = Integer.valueOf(String.valueOf(entry.get("id")));
        this.title = String.valueOf(entry.get("title"));
        this.status = String.valueOf(entry.get("status"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
