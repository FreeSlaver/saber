package com.sparkjava.tutorial.todos.service;

import com.sparkjava.tutorial.todos.vo.Todo;

import java.util.List;
import java.util.Map;

/**
 * Created by 00013708 on 2017/11/11.
 */
public interface TodoService {

    void add(String title);

    void del(int id);

    void edit(Todo todo);

    List<Todo> list(String status);

    int count(String status);

    Map<String,Object> model(String status);
}
