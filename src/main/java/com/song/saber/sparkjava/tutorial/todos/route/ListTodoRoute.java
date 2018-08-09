//package com.sparkjava.tutorial.todos.route;
//
//import com.sparkjava.tutorial.todos.redis.RedisService;
//import com.sparkjava.tutorial.todos.vo.TodoVo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import spark.ModelAndView;
//import spark.Request;
//import spark.Response;
//import spark.TemplateViewRoute;
//
//import java.util.*;
//
///**
// * Created by 00013708 on 2017/11/10.
// */
//@Service
//public class ListTodoRoute implements TemplateViewRoute {
//    @Autowired
//    private RedisService<String, String> redisService;
//
//    @Override
//    public ModelAndView handle(Request request, Response response) throws Exception {
//        //得到所有的id，然后根据id集合得到所有的todo之后全部查询出来
//        Set<String> todoKeys = redisService.keys("todos*");
//        if(todoKeys==null||todoKeys.isEmpty()){
//            return null;
//        }
//        List<TodoVo> list = new ArrayList<>();
//        for(String key:todoKeys){
//            Map<Object,Object> entry = redisService.hEntry(key);
//            TodoVo todoVo = new TodoVo(entry);
//            list.add(todoVo);
//        }
//        Map<String,Object> modelMap = new HashMap<>();
//        modelMap.put("list",list);
//
//        return new ModelAndView(modelMap,"list");
//    }
//}
