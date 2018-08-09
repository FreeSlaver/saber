//package com.sparkjava.tutorial.todos.route;
//
//import com.sparkjava.tutorial.todos.redis.RedisService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import spark.ModelAndView;
//import spark.Request;
//import spark.Response;
//import spark.TemplateViewRoute;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class CreateTodoRoute implements TemplateViewRoute {
//
//    @Autowired
//    private RedisService<String, String> redisService;
//
//    public ModelAndView handle(Request request, Response response) throws Exception {
//        String createTodo = request.queryParams("createTodo");
//        if (createTodo == null) {
//            throw new Exception("need input todo");
//        }
//        try {
//            long nextTodoId = redisService.increment("nextTodoId", 1);
//            String key = "todos:" + nextTodoId;
//            Map<String, Object> map = new HashMap<>();
//            map.put("id", String.valueOf(nextTodoId));
//            map.put("content", createTodo);
//            map.put("status", "todo");
//            redisService.hmput(key, map);
//
//            return new ModelAndView(map, "index");
//        } catch (Exception e) {
//            e.printStackTrace();
//            redisService.increment("nextTodoId", -1);
//            throw e;
//        }
//    }
//}
