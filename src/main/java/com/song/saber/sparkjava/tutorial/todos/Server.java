package com.sparkjava.tutorial.todos;

import com.sparkjava.tutorial.todos.service.TodoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spark.ModelAndView;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.Map;

import static spark.Spark.*;


public class Server {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        ConfigService configService = context.getBean(ConfigService.class);
        Spark.port(configService.getServerPort());
        Spark.threadPool(configService.getMaxThreads(), configService.getMinThreads(),
                configService.getIdleTimeoutMillis());

        TodoService todoService = context.getBean(TodoService.class);
        staticFiles.location("/public");
        Spark.get("/ping", (req, res) -> "pong");

        get("/",                        (req, res)      -> {
            String isICReq = req.queryParams("ic-request");
            String status = req.queryParams("status");
            Map<String,Object> model = todoService.model(status);
            return renderTodos(model);
        });

        post("/todos", (req, res) -> {
            String isICReq = req.queryParams("ic-request");
            String status = req.queryParams("status");
            String todoTitle = req.queryParams("todo-title");
            todoService.add(todoTitle);

            Map<String,Object> model = todoService.model(status);
            return renderTodos(model);
        });

    }


    private static String renderTodos(Map<String,Object> model) {
        if ("true".equals(false)) {
            return renderTemplate("velocity/todoList.vm", model);
        }
        return renderTemplate("velocity/index.vm", model);
    }
    private static String renderTemplate(String template, Map model) {
        return new VelocityTemplateEngine().render(new ModelAndView(model, template));
    }

}
