package com.zj.quiz_community.controller;

import com.zj.quiz_community.async.EventModel;
import com.zj.quiz_community.async.EventProducer;
import com.zj.quiz_community.async.EventType;
import com.zj.quiz_community.dto.HostHolder;
import com.zj.quiz_community.dto.ViewObject;
import com.zj.quiz_community.pojo.EntityType;
import com.zj.quiz_community.pojo.Question;
import com.zj.quiz_community.pojo.User;
import com.zj.quiz_community.service.CommentService;
import com.zj.quiz_community.service.QuestionService;
import com.zj.quiz_community.service.UserService;
import com.zj.quiz_community.service.impl.followService;
import com.zj.quiz_community.utils.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.ObjectView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaojie
 * @date 2018\10\28 0028 - 16:34
 */
@Controller
public class FollowController {

    @Autowired
    HostHolder hostHolder;

    @Autowired
    followService followService;

     @Autowired
     EventProducer eventProducer;

     @Autowired
     QuestionService questionService;

     @Autowired
     UserService userService;

     @Autowired
     CommentService commentService;

    @RequestMapping(value = "/followUser",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String followUser(@RequestParam("userId") Integer userId) throws IOException {

         if(hostHolder.getUser() == null){
             return MyUtil.getJsonObject(999);
         }

        boolean follow = followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId);

         eventProducer.fireEvent(new EventModel(EventType.FOLLOW).setActorId(hostHolder.getUser().getId())
                                 .setEntityId(userId).setEntityType(EntityType.ENTITY_USER)
                                 .setEntityOwerId(userId));

       return MyUtil.getJsonObject(follow? 0:1,String.valueOf(followService.getFolloweeCount(userId,EntityType.ENTITY_USER)));

    }

    @RequestMapping(value = "/unfollowUser",method = RequestMethod.POST)
    @ResponseBody
    public String unFollowUser(@RequestParam("userId") Integer userId) throws IOException {

        if(hostHolder.getUser() == null){
            return MyUtil.getJsonObject(999);
        }

        boolean follow = followService.unfollow(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId);

//        eventProducer.fireEvent(new EventModel(EventType.UNFOLLOW).setActorId(hostHolder.getUser().getId())
//                .setEntityId(userId).setEntityType(EntityType.ENTITY_USER)
//                .setEntityOwerId(userId));

        return MyUtil.getJsonObject(follow? 0:1,String.valueOf(followService.getFolloweeCount(userId,EntityType.ENTITY_USER)));

    }


    @RequestMapping(value = "/followQuestion",method = RequestMethod.POST)
    @ResponseBody
    public String followQuestion(@RequestParam("questionId") Integer questionId) throws IOException {

        if(hostHolder.getUser() == null){
            return MyUtil.getJsonObject(999);
        }

        Question question = questionService.getQuestionById(questionId);

        if(question == null){

            return MyUtil.getJsonObject(1,"问题不存在");

        }


        boolean follow = followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, questionId);

        eventProducer.fireEvent(new EventModel(EventType.FOLLOW).setActorId(hostHolder.getUser().getId())
                .setEntityId(questionId).setEntityType(EntityType.ENTITY_QUESTION)
                .setEntityOwerId(questionId));

        Map<String,Object> info = new HashMap<>();

        info.put("headUrl", hostHolder.getUser().getHeadUrl());
        info.put("name", hostHolder.getUser().getName());
        info.put("id", hostHolder.getUser().getId());
        info.put("count", followService.getFollowerCount(EntityType.ENTITY_QUESTION, questionId));

        return MyUtil.getJsonObject(follow?0:1,info);
    }


    @RequestMapping(value = "/unfollowQuestion",method = RequestMethod.POST)
    @ResponseBody
    public String unfollowQuestion(@RequestParam("questionId") Integer questionId) throws IOException {

        if(hostHolder.getUser() == null){
            return MyUtil.getJsonObject(999);
        }

        Question question = questionService.getQuestionById(questionId);

        if(question == null){

            return MyUtil.getJsonObject(1,"问题不存在");

        }


        boolean follow = followService.unfollow(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, questionId);

//        eventProducer.fireEvent(new EventModel(EventType.UNFOLLOW).setActorId(hostHolder.getUser().getId())
//                .setEntityId(questionId).setEntityType(EntityType.ENTITY_QUESTION)
//                .setEntityOwerId(questionId));

        Map<String,Object> info = new HashMap<>();

        info.put("id", hostHolder.getUser().getId());
        info.put("count", followService.getFollowerCount(EntityType.ENTITY_QUESTION, questionId));

        return MyUtil.getJsonObject(follow ? 0:1,info);
    }

    @RequestMapping(value = "/user/{uid}/followers",method = RequestMethod.GET)
    public String followers(Model model, @PathVariable("uid") Integer userId){

        List<Integer> followerIds = followService.getfollowers(EntityType.ENTITY_USER, userId, 0,10);

        if(hostHolder.getUser()!=null){
            model.addAttribute("followers",getUserInfo(hostHolder.getUser().getId(),followerIds));
        }else {
            model.addAttribute("followers",getUserInfo(0,followerIds));
        }

        model.addAttribute("followerCount",followService.getFollowerCount(EntityType.ENTITY_USER,userId));
        model.addAttribute("curUser",userService.selectById(userId));

        return "followers";
    }

    @RequestMapping(value = "/user/{uid}/followees",method = RequestMethod.GET)
    public String followees(Model model, @PathVariable("uid") Integer userId){

        List<Integer> followeeIds = followService.getfollowees(userId, EntityType.ENTITY_USER,0,10);

        if(hostHolder.getUser()!=null){
            model.addAttribute("followees",getUserInfo(hostHolder.getUser().getId(),followeeIds));
        }else {
            model.addAttribute("followees",getUserInfo(0,followeeIds));
        }

        model.addAttribute("followeeCount",followService.getFolloweeCount(userId,EntityType.ENTITY_USER));
        model.addAttribute("curUser",userService.selectById(userId));

        return "followees";
    }




    public List<ViewObject> getUserInfo(int localUserId , List<Integer> userIds){

        List<ViewObject> vos = new ArrayList<>();

        for(Integer uid:userIds){

            User user = userService.selectById(uid);

            if(user == null){
                continue;
            }

            ViewObject vo = new ViewObject();

            vo.set("user",user);
            vo.set("commentCount",commentService.getCountByUserId(uid));
            vo.set("followerCount",followService.getFollowerCount(EntityType.ENTITY_USER,uid));
            vo.set("followeeCount",followService.getFolloweeCount(uid,EntityType.ENTITY_USER));

            if(localUserId!=0){
                vo.set("followed",followService.isFollower(localUserId,EntityType.ENTITY_USER,uid));
            }else {
                vo.set("followed",false);
            }

            vos.add(vo);
        }
        return vos;
     }



}
