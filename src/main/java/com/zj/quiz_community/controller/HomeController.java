package com.zj.quiz_community.controller;


import com.zj.quiz_community.dto.ViewObject;
import com.zj.quiz_community.pojo.Question;
import com.zj.quiz_community.pojo.User;
import com.zj.quiz_community.service.QuestionService;
import com.zj.quiz_community.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojie
 * @date 2018\10\6 0006 - 23:24
 */
@Controller
public class HomeController {

  private final Logger log = LoggerFactory.getLogger(HomeController.class);

  @Autowired
  private UserService userService;

  @Autowired
  private QuestionService questionService;


   public List<ViewObject> getQuestions(Integer userId, Integer offset, Integer limit){

       List<Question> questionList = questionService.getLatestQuestion(userId, offset, limit);

       List<ViewObject> vos = new ArrayList<>();

       for (Question question : questionList){

          ViewObject vo = new ViewObject();

          vo.set("question",question);

          User user = userService.selectById(question.getUserId());

          vo.set("user",user);

          vos.add(vo);
       }

        return vos;
   }


   @RequestMapping(value = {"/","/index"},method = {RequestMethod.GET,RequestMethod.POST})
   public String index(Model model){

       List<ViewObject> questions = getQuestions(0, 0, 10);
       model.addAttribute("vos",questions);
       return "index";
   }

   @RequestMapping(value = "/user/{userId}",method ={RequestMethod.GET ,RequestMethod.POST})
   public String userIndex(@PathVariable("userId") Integer userId,Model model){

       List<ViewObject> userQuestion = getQuestions(userId, 0, 10);
       model.addAttribute("vos",userQuestion);
       return "index";
   }

}
