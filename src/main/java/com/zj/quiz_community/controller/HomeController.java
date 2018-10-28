package com.zj.quiz_community.controller;


import com.zj.quiz_community.dto.HostHolder;
import com.zj.quiz_community.dto.ViewObject;
import com.zj.quiz_community.pojo.EntityType;
import com.zj.quiz_community.pojo.Question;
import com.zj.quiz_community.pojo.User;
import com.zj.quiz_community.service.CommentService;
import com.zj.quiz_community.service.QuestionService;
import com.zj.quiz_community.service.UserService;
import com.zj.quiz_community.service.impl.followService;
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

  @Autowired
   private followService followService;

  @Autowired
  private  HostHolder hostHolder;

  @Autowired
  private CommentService commentService;

   public List<ViewObject> getQuestions(Integer userId, Integer offset, Integer limit){

       List<Question> questionList = questionService.getLatestQuestion(userId, offset, limit);

       List<ViewObject> vos = new ArrayList<>();

       for (Question question : questionList){

          ViewObject vo = new ViewObject();

          vo.set("question",question);

          vo.set("followCount",followService.getFollowerCount(EntityType.ENTITY_QUESTION,question.getId()));

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
       User user = userService.selectById(userId);
       ViewObject vo = new ViewObject();

       vo.set("user",user);
       vo.set("followerCount",followService.getFollowerCount(EntityType.ENTITY_USER,userId));
       vo.set("followeeCount", followService.getFolloweeCount(userId, EntityType.ENTITY_USER));
       vo.set("commentCount",commentService.getCountByUserId(userId));
       if (hostHolder.getUser() != null) {
           vo.set("followed", followService.isFollower(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId));
       } else {
           vo.set("followed", false);
       }
       model.addAttribute("profileUser", vo);
       return "profile";
   }

}
