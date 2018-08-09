package com.sparkjava.tutorial.todos.service.impl;

import com.sparkjava.tutorial.todos.redis.RedisService;
import com.sparkjava.tutorial.todos.service.TodoService;
import com.sparkjava.tutorial.todos.vo.Todo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by 00013708 on 2017/11/11.
 */
@Service
public class TodoServiceImpl implements TodoService {
    @Autowired
    private RedisService<String, String> redisService;

    @Override
    public void add(String title) {
        try {
            long nextTodoId = redisService.increment("nextTodoId", 1);
            String key = "todos:" + nextTodoId;
            Map<String, Object> map = new HashMap<>();
            map.put("id", String.valueOf(nextTodoId));
            map.put("title", title);
            map.put("status", "active");
            redisService.hmput(key, map);
        } catch (Exception e) {
            e.printStackTrace();
            redisService.increment("nextTodoId", -1);
            throw e;
        }
    }

    @Override
    public void del(int id) {
        String key = "todos:" + id;
        redisService.remove(key);
    }

    @Override
    public void edit(Todo todo) {
        int id = todo.getId();
        if (id == 0) {
            throw new IllegalArgumentException("id null");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("title", todo.getTitle());
        map.put("status", todo.getStatus());

        String key = "todos:" + id;
        redisService.hmput(key, map);
    }

    @Override
    public List<Todo> list(String status) {
        Set<String> todoKeys = redisService.keys("todos*");
        if (todoKeys == null || todoKeys.isEmpty()) {
            return null;
        }
        List<Todo> list = new ArrayList<>();
        for (String key : todoKeys) {
            Map<Object, Object> entry = redisService.hEntry(key);
            String statusTemp = String.valueOf(entry.get("status"));
            if (StringUtils.isEmpty(status) || status.equalsIgnoreCase(statusTemp)) {
                Todo todoVo = new Todo(entry);
                list.add(todoVo);
            }
        }
        return list;
    }

    @Override
    public int count(String status) {
        return list(status).size();
    }

    @Override
    public Map<String, Object> model(String status) {
        Map<String, Object> model = new HashMap<>();
        model.put("status", status);
        model.put("todos", list(status));
        model.put("activeCount", count("active"));

        model.put("anyCompleteTodos", count("complete") > 0 ? true : false);
        return model;
    }
}
